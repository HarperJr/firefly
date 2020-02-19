package com.conceptic.firefly.di

import com.conceptic.firefly.app.Application
import com.conceptic.firefly.app.GameController
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
    scope(named<Application>()) {
        /**
         * Publishers
         */
        scoped { ScreenUpdatesPublisher() }
        scoped { KeyActionsPublisher() }
        scoped { MouseActionsPublisher() }


        /**
         * Controllers
         */
        scoped { ScreenController(get(), get(), get()) }
        scoped { GameController(get(), get(), get()) }

        scoped { Application(get(), get()) }
    }
}
