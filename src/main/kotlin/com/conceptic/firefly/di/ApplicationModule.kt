package com.conceptic.firefly.di

import com.conceptic.firefly.app.Application
import com.conceptic.firefly.app.camera.CameraController
import com.conceptic.firefly.app.gl.GLController
import com.conceptic.firefly.app.gl.renderable.mesh.loader.MeshContentProvider
import com.conceptic.firefly.app.gl.renderable.mesh.loader.MeshLoader
import com.conceptic.firefly.app.gl.shader.ShaderStore
import com.conceptic.firefly.app.gl.shader.loader.ShaderContentProvider
import com.conceptic.firefly.app.gl.shader.loader.ShaderLoader
import com.conceptic.firefly.app.gl.texture.TextureContentProvider
import com.conceptic.firefly.app.gl.texture.TextureLoader
import com.conceptic.firefly.app.gl.texture.TextureStore
import com.conceptic.firefly.screen.ScreenController
import com.conceptic.firefly.screen.support.KeyActionsPublisher
import com.conceptic.firefly.screen.support.MouseActionsPublisher
import com.conceptic.firefly.screen.support.ScreenUpdatesPublisher
import com.conceptic.firefly.utils.ExternalStorageFileProvider
import com.conceptic.firefly.utils.ResourcesFileProvider
import org.koin.core.qualifier.named
import org.koin.dsl.module

val applicationModule = module {
    single { ShaderStore() }
    single { TextureStore() }

    factory { CameraController() }

    single {
        MeshLoader(
            MeshContentProvider.fromFileProvider(
                get()
            ), get()
        )
    }
    single { ShaderLoader(ShaderContentProvider.fromFileProvider(get()), get()) }
    single { TextureLoader(TextureContentProvider.fromFileProvider(get()), get()) }

    scope(named<Application>()) {
        /**
         * Publishers
         */
        scoped { ScreenUpdatesPublisher() }
        scoped { KeyActionsPublisher() }
        scoped { MouseActionsPublisher() }

        /**
         * Other
         */
        scoped {
            when {
                ResourcesFileProvider.available -> ResourcesFileProvider
                ExternalStorageFileProvider.available -> ExternalStorageFileProvider
                else -> throw IllegalStateException("No file provider available, check security permission, or restart app as admin")
            }
        }

        /**
         * Controllers
         */
        scoped { ScreenController(get(), get()) }
        scoped { GLController(get(), get()) }

        scoped { Application(get(), get()) }
    }
}
