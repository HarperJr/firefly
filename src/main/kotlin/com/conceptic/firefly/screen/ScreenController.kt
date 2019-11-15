package com.conceptic.firefly.screen

class ScreenController {
    private var screenWidth = DEFAULT_SCREEN_WIDTH
        set(value) {
            field = value
            screen?.notifyDimensionChanged(screenWidth, screenHeight)
        }
    private var screenHeight = DEFAULT_SCREEN_HEIGHT
        set(value) {
            field = value
            screen?.notifyDimensionChanged(screenWidth, screenHeight)
        }
    private var screen: GLFWScreen? = null

    fun init() {
        screen = GLFWScreen.Builder()
            .setTitle(TITLE)
            .setDimension(screenWidth, screenHeight)
            .setFullscreen(false)
            .build()
    }

    fun addKeyActionCallback(callback: (Keys, KeyAction) -> Unit) = screen?.addKeyActionCallback(callback)

    fun removeKeyActionCallback(callback: (Keys, KeyAction) -> Unit) = screen?.removeKeyActionCallback(callback)

    fun show() = screen?.showWindow() ?: throw IllegalStateException("Window is not initiated")


    fun isActive() = screen?.isActive()

    fun swapBuffers() = screen?.update()

    fun destroy() {
        screen?.destroy() ?: throw IllegalStateException("Window is already destroyed")
        screen = null
    }

    companion object {
        const val TITLE = "Firefly"
        const val DEFAULT_SCREEN_WIDTH = 640
        const val DEFAULT_SCREEN_HEIGHT = 480
    }
}