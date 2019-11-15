package com.conceptic.firefly.app

import com.conceptic.firefly.app.gl.GLSurfaceRenderer
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
    private val glSurfaceRenderer: GLSurfaceRenderer
) : KoinComponent {
    private val logger = Logger.getLogger<Application>()
    private val fixedUpdatesExecutor = Executors.newSingleThreadExecutor()

    private var needsUpdates = false

    fun run() {
        init()
        runLoop()
    }

    private fun runLoop() {
        needsUpdates = true
        runCatching {
            screenController.show()
            runFixedUpdates()
            while (needsUpdates) {
                screenController.update()
                needsUpdates = screenController.isActive()
            }
        }.onSuccess {
            fixedUpdatesExecutor.shutdown()
            screenController.destroy()
        }.onFailure { logger.error(it) }
    }

    private fun runFixedUpdates() {
        fixedUpdatesExecutor.submit {
            kotlin.runCatching {
                while (needsUpdates) {
                    glSurfaceRenderer.updateAsync()
                    Thread.sleep(FIXED_UPDATES_COOLDOWN)
                }
            }
        }
    }

    private fun init() {
        screenController.init()
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