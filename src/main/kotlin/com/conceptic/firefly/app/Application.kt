package com.conceptic.firefly.app

import com.conceptic.firefly.di.applicationModule
import com.conceptic.firefly.log.Logger
import com.conceptic.firefly.screen.ScreenController
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named

/**
 * Main class for application instance
 */
class Application : KoinComponent {
    private val logger = Logger.getLogger<Application>()

    private val scope = getKoin().getOrCreateScope(APP_SCOPE, named<Application>())
    private val screenController by scope.inject<ScreenController>()

    private var running = false

    fun run() {
        init()
        runLoop()
    }

    private fun runLoop() {
        running = true

        runCatching {
            screenController.show()
            while (screenController.isActive() && running) {

                screenController.update()
            }
        }.onSuccess {
            screenController.destroy()
            getKoin().deleteScope(APP_SCOPE)
        }.onFailure { logger.e(it) }
    }

    private fun init() {
        screenController.init()
    }

    companion object {
        private const val APP_SCOPE = "application_scope"

        fun runApplication() = startKoin { modules(applicationModule) }
            .also {
                val application = it.koin.get<Application>()
                application.run()
            }
    }
}