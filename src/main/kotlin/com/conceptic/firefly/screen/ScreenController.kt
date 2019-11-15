package com.conceptic.firefly.screen

import com.conceptic.firefly.screen.support.KeyActionsPublisher
import com.conceptic.firefly.screen.support.ScreenUpdatesPublisher

class ScreenController(
    private val screenUpdatesPublisher: ScreenUpdatesPublisher,
    private val keyActionsPublisher: KeyActionsPublisher
) {
    private var screenWidth = DEFAULT_SCREEN_WIDTH
    private var screenHeight = DEFAULT_SCREEN_HEIGHT

    private var screen: GLFWScreen? = null

    fun init() {
        screen = GLFWScreen.Builder()
            .setTitle(TITLE)
            .setFullscreen(false)
            .setDimension(screenWidth, screenHeight)
            .setKeyActionCallback(onKeyActionCallback)
            .setScreenActionCallback(onScreenCommandCallback)
            .build()
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

    private val onKeyActionCallback = { key: Keys, keyAction: KeyAction ->
        keyActionsPublisher.notify {
            when (keyAction) {
                KeyAction.PRESS -> onKeyPressed(key)
                KeyAction.RELEASE -> onKeyReleased(key)
            }
        }
    }

    private val onScreenCommandCallback = { action: ScreenAction ->
        screenUpdatesPublisher.notify {
            when (action) {
                is InitAction -> onScreenInit()
                is ShowAction -> onScreenShow()
                is UpdateAction -> onScreenUpdate()
                is DestroyAction -> onScreenDestroy()
                is SizeChangeAction -> onScreenSizeChanged(action.width, action.height)
            }
        }
    }

    companion object {
        const val TITLE = "Firefly"
        const val DEFAULT_SCREEN_WIDTH = 640
        const val DEFAULT_SCREEN_HEIGHT = 480
    }
}