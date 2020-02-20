package com.conceptic.firefly.app

import com.conceptic.firefly.Publishers
import com.conceptic.firefly.app.game.GameController
import com.conceptic.firefly.app.screen.ScreenController
import com.conceptic.firefly.log.Logger
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Main class for application instance
 */
class Application {
    private val logger = Logger.getLogger<Application>()

    private val publishers = Publishers()
    private val fixedUpdatesExecutor = Executors.newSingleThreadExecutor()

    private val screenController = ScreenController(publishers)
    private val gameController = GameController(publishers)
    private val needsUpdates: AtomicBoolean = AtomicBoolean(false)

    fun run() {
        screenController.init()

        GL.createCapabilities()
        GL11.glClearColor(0.4f, 0.8f, 0.9f, 1.0f)
        GL11.glEnable(GL11.GL_TEXTURE_2D)
        GL11.glEnable(GL11.GL_DEPTH_TEST)

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
        }.onFailure {
            logger.error(it)
        }

        shutdown()
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
        private const val FIXED_UPDATES_COOLDOWN = 1000L

        fun runApplication() {
            val application = Application()
            application.run()
        }
    }
}