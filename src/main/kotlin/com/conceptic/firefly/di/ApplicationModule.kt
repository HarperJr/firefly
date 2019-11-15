package com.conceptic.firefly.di

import com.conceptic.firefly.app.Application
import com.conceptic.firefly.screen.ScreenController
import org.koin.core.qualifier.named
import org.koin.dsl.module

val applicationModule = module {
    single { Application() }

    scope(named<Application>()) {
        factory { ScreenController() }
    }
}