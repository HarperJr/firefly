package com.conceptic.firefly.app

import com.conceptic.firefly.Publishers
import com.conceptic.firefly.app.game.GameController
import com.conceptic.firefly.app.screen.GLFWScreen
import com.conceptic.firefly.app.screen.support.ScreenUpdatesSubscriber
import com.conceptic.firefly.log.Logger
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Main class for application instance
 */
class Application(defScreenWidth: Int, defScreenHeight: Int, defTitle: String) : ScreenUpdatesSubscriber {
    private val logger = Logger.getLogger<Application>()

    private val publishers = Publishers()
    private val fixedUpdatesExecutor = Executors.newSingleThreadExecutor()

    private val screen = GLFWScreen(defScreenWidth, defScreenHeight, defTitle, false)
    private val gameController = GameController(publishers)
    private val needsUpdates: AtomicBoolean = AtomicBoolean(false)

    fun run() {
        initScreenSubscribers()
        publishers.screenUpdatesPublisher.subscribe(this)

        needsUpdates.set(true)
        runCatching {
            screen.show()
            while (needsUpdates.get()) {
                //Just update the screen and all subscribers to screen's updates will update theirs states
                if (screen.isActive()) {
                    screen.update()
                } else needsUpdates.set(false)
            }
        }.onFailure {
            logger.error(it)
        }

        shutdown()
    }

    override fun onShow() {
        gameController.onShow()
        fixedUpdatesExecutor.submit(fixedUpdatesRunnable)
    }

    override fun onUpdate() {
        gameController.onUpdate()
    }

    override fun onDestroy() {
        gameController.onDestroy()
        fixedUpdatesExecutor.shutdown()
    }

    override fun onSizeChanged(width: Int, height: Int) {
        gameController.onSizeChanged(width, height)
    }

    private fun initScreenSubscribers() {
        with(screen) {
            screenListener = publishers.screenUpdatesPublisher
            keysListener = publishers.keyActionsPublisher
            mouseListener = publishers.mouseActionsPublisher
        }
    }

    private fun shutdown() {
        logger.info("Shutting down")
        screen.destroy()
    }

    private val fixedUpdatesRunnable = Runnable {
        kotlin.runCatching {
            var currentTimeMillis = System.currentTimeMillis()
            while (needsUpdates.get()) {
                if (currentTimeMillis + FIXED_UPDATES_COOLDOWN >= System.currentTimeMillis()) {
                    gameController.onFixedUpdate()
                    currentTimeMillis = System.currentTimeMillis()
                }
            }
        }
    }

    companion object {
        private const val DEF_SCREEN_WIDTH = 1020
        private const val DEF_SCREEN_HEIGHT = 840
        private const val DEF_TITLE = "Spaceships"
        private const val FIXED_UPDATES_COOLDOWN = 1000L

        fun runApplication() {
            val application = Application(DEF_SCREEN_WIDTH, DEF_SCREEN_HEIGHT, DEF_TITLE)
            application.run()
        }
    }
}