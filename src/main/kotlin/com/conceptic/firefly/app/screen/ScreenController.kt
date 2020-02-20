package com.conceptic.firefly.app.screen

import com.conceptic.firefly.Publishers
import com.conceptic.firefly.app.screen.listener.KeyActionsListener
import com.conceptic.firefly.app.screen.listener.MouseActionsListener
import com.conceptic.firefly.app.screen.listener.ScreenActionsListener

class ScreenController(
    private val publishers: Publishers
) {
    private var screenWidth = DEFAULT_SCREEN_WIDTH
    private var screenHeight = DEFAULT_SCREEN_HEIGHT

    private var screen: GLFWScreen = GLFWScreen(
        screenWidth,
        screenHeight,
        TITLE,
        false,
        KeyActionsListenerImpl(),
        ScreenActionsListenerImpl(),
        MouseActionsListenerImpl()
    )

    fun init() = screen.init()

    fun show() {
        screen.showWindow()
    }

    fun update() {
        screen.update()
    }

    fun isActive() = screen.isActive()

    fun destroy() = screen.destroy()

    inner class KeyActionsListenerImpl : KeyActionsListener {
        private val keyActionsPublisher = publishers.keyActionsPublisher

        override fun onPressed(key: Key) {
            keyActionsPublisher.notify { onPressed(key) }
        }

        override fun onReleased(key: Key) {
            keyActionsPublisher.notify { onReleased(key) }
        }
    }

    inner class ScreenActionsListenerImpl : ScreenActionsListener {
        private val screenUpdatesPublisher = publishers.screenUpdatesPublisher

        override fun onSizeChanged(width: Int, height: Int) =
            screenUpdatesPublisher.notify { onSizeChanged(width, height) }

        override fun onShow(width: Int, height: Int) {
            screenUpdatesPublisher.notify { onShow(width, height) }
        }

        override fun onInit() {
            screenUpdatesPublisher.notify { onInit() }
        }

        override fun onUpdate() {
            screenUpdatesPublisher.notify { onUpdate() }
        }

        override fun onDestroy() {
            screenUpdatesPublisher.notify { onDestroy() }
        }
    }

    inner class MouseActionsListenerImpl : MouseActionsListener {
        private val mouseActionsPublisher = publishers.mouseUpdatesPublisher

        override fun onClicked(x: Int, y: Int) {
            mouseActionsPublisher.notify { onClicked(x, y) }
        }

        override fun onDoubleClicked(x: Int, y: Int) {
            mouseActionsPublisher.notify { onDoubleClicked(x, y) }
        }

        override fun onMoved(x: Int, y: Int) {
            mouseActionsPublisher.notify { onMoved(x, y) }
        }
    }

    companion object {
        const val TITLE = "Firefly"
        const val DEFAULT_SCREEN_WIDTH = 640
        const val DEFAULT_SCREEN_HEIGHT = 480
    }
}