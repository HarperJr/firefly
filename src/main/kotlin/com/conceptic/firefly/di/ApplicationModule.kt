package com.conceptic.firefly.di

import com.conceptic.firefly.app.Application
import com.conceptic.firefly.app.camera.CameraController
import com.conceptic.firefly.app.gl.GLSurfaceRenderer
import com.conceptic.firefly.screen.ScreenController
import com.conceptic.firefly.screen.support.KeyActionsPublisher
import com.conceptic.firefly.screen.support.MouseActionsPublisher
import com.conceptic.firefly.screen.support.ScreenUpdatesPublisher
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
        factory { ScreenController(get(), get()) }
        factory { CameraController(get()) }

        /**
         * Renderers
         */
        factory { GLSurfaceRenderer(get()) }

        scoped { Application(get(), get()) }
    }
}