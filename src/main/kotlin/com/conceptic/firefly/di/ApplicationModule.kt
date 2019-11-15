package com.conceptic.firefly.di

import com.conceptic.firefly.app.Application
import com.conceptic.firefly.app.gl.GLSurfaceRenderer
import com.conceptic.firefly.screen.ScreenController
import com.conceptic.firefly.screen.support.KeyActionsPublisher
import com.conceptic.firefly.screen.support.ScreenUpdatesPublisher
import org.koin.core.qualifier.named
import org.koin.dsl.module

val applicationModule = module {
    scope(named<Application>()) {
        scoped { ScreenUpdatesPublisher() }
        scoped { KeyActionsPublisher() }

        factory { ScreenController(get(), get()) }
        factory { GLSurfaceRenderer(get()) }

        scoped { Application(get(), get()) }
    }
}