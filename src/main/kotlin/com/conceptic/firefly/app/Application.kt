package com.conceptic.firefly.app

import com.conceptic.firefly.app.game.GameController
import com.conceptic.firefly.di.applicationModule
import com.conceptic.firefly.log.Logger
import com.conceptic.firefly.screen.ScreenController
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Main class for application instance
 */
class Application(
    private val screenController: ScreenController,
    private val gameController: GameController
) : KoinComponent {
    private val logger = Logger.getLogger<Application>()
    private val fixedUpdatesExecutor = Executors.newSingleThreadExecutor()

    private val needsUpdates: AtomicBoolean = AtomicBoolean(false)

    fun run() {
        screenController.init()
        gameController.init()

        needsUpdates.set(true)
        runCatching {
            screenController.show()
            runFixedUpdates()
            while (needsUpdates.get()) {
                //Just update the screen and all subscribers to screen's updates will update theirs states
                screenController.update()
                needsUpdates.set(screenController.isActive())
            }
        }.onSuccess { shutdown() }
            .onFailure {
                shutdown()
                logger.error(it)
            }
    }

    private fun shutdown() {
        logger.info("Shutting down")
        fixedUpdatesExecutor.shutdown()
        screenController.destroy()
    }

    private fun runFixedUpdates() {
        var currentTimeMillis = System.currentTimeMillis()
        fixedUpdatesExecutor.submit {
            kotlin.runCatching {
                while (needsUpdates.get()) {
                    if (currentTimeMillis + FIXED_UPDATES_COOLDOWN >= System.currentTimeMillis()) {
                        gameController.updateAsync()
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