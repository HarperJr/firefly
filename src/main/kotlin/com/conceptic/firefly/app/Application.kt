package com.conceptic.firefly.app

import com.conceptic.firefly.app.gl.GLController
import com.conceptic.firefly.di.applicationModule
import com.conceptic.firefly.log.Logger
import com.conceptic.firefly.screen.ScreenController
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import java.util.concurrent.Executors

/**
 * Main class for application instance
 */
class Application(
    private val screenController: ScreenController,
    private val glController: GLController
) : KoinComponent {
    private val logger = Logger.getLogger<Application>()
    private val fixedUpdatesExecutor = Executors.newSingleThreadExecutor()

    private var needsUpdates = false

    fun run() {
        screenController.init()
        glController.init()

        needsUpdates = true
        runCatching {
            screenController.show()
            runFixedUpdates()
            while (needsUpdates) {
                //Just update the screen and all subscribers to screen's updates will update theirs states
                screenController.update()
                needsUpdates = screenController.isActive()
            }
        }.onSuccess {
            fixedUpdatesExecutor.shutdown()
            screenController.destroy()
        }.onFailure { logger.error(it) }
    }

    private fun runFixedUpdates() {
        var currentTimeMillis = System.currentTimeMillis()
        fixedUpdatesExecutor.submit {
            kotlin.runCatching {
                while (needsUpdates) {
                    if (currentTimeMillis + FIXED_UPDATES_COOLDOWN >= System.currentTimeMillis()) {
                        glController.updateAsync()
                        currentTimeMillis = System.currentTimeMillis()
                    }
                }
            }
        }
    }

    companion object {
        private const val APP_SCOPE = "application_scope"
        private const val FIXED_UPDATES_COOLDOWN = 1000L

        fun runApplication() = startKoin { modules(applicationModule) }
            .also {
                val appScope = it.koin.createScope(APP_SCOPE, named<Application>())
                val application = appScope.get<Application>()
                application.run()
                it.koin.deleteScope(APP_SCOPE)
            }
    }
}