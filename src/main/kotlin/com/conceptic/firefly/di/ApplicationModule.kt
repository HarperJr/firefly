package com.conceptic.firefly.di

import com.conceptic.firefly.app.Application
import com.conceptic.firefly.app.game.GameController
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
        scoped { ScreenController(get(), get(), get()) }
        scoped { GameController(get(), get(), get()) }

        scoped { Application(get(), get()) }
    }
}
