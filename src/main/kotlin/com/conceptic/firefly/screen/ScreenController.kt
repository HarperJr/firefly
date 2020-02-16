package com.conceptic.firefly.screen

import com.conceptic.firefly.screen.support.KeyActionsPublisher
import com.conceptic.firefly.screen.support.MouseActionsPublisher
import com.conceptic.firefly.screen.support.ScreenUpdatesPublisher

class ScreenController(
    private val screenUpdatesPublisher: ScreenUpdatesPublisher,
    private val keyActionsPublisher: KeyActionsPublisher,
    private val mouseActionsPublisher: MouseActionsPublisher
) {
    private var screenWidth = DEFAULT_SCREEN_WIDTH
    private var screenHeight = DEFAULT_SCREEN_HEIGHT

    private var screen: GLFWScreen? = null

    fun init() {
        screen = GLFWScreen(
            TITLE, screenWidth, screenHeight, false,
            keyActionListener, screenActionListener, mouseActionListener
        )
    }

    fun show() {
        screen?.showWindow() ?: throw IllegalStateException("Window is not initiated")
    }

    fun update() {
        screen?.update()
    }

    fun isActive() = screen?.isActive() ?: false

    fun destroy() {
        screen?.destroy() ?: throw IllegalStateException("Window is already destroyed")
        screen = null
    }

    private val keyActionListener = object : KeyActionListener {
        override fun onPressed(key: Key) = keyActionsPublisher.notify { onPressed(key) }

        override fun onReleased(key: Key) = keyActionsPublisher.notify { onReleased(key) }
    }

    private val screenActionListener = object : ScreenActionListener {
        override fun onSizeChanged(width: Int, height: Int) =
            screenUpdatesPublisher.notify { onSizeChanged(width, height) }

        override fun onInit() = screenUpdatesPublisher.notify { onInit() }

        override fun onShow() = screenUpdatesPublisher.notify { onShow() }

        override fun onUpdate() = screenUpdatesPublisher.notify { onUpdate() }

        override fun onDestroy() = screenUpdatesPublisher.notify { onDestroy() }
    }

    private val mouseActionListener = object : MouseActionListener {
        override fun onClicked(x: Int, y: Int) = mouseActionsPublisher.notify { onClicked(x, y) }

        override fun onDoubleClicked(x: Int, y: Int) = mouseActionsPublisher.notify { onDoubleClicked(x, y) }

        override fun onMoved(x: Int, y: Int) = mouseActionsPublisher.notify { onMoved(x, y) }
    }

    companion object {
        const val TITLE = "Firefly"
        const val DEFAULT_SCREEN_WIDTH = 640
        const val DEFAULT_SCREEN_HEIGHT = 480
    }
}