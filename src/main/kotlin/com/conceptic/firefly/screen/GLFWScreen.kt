package com.conceptic.firefly.screen

import com.conceptic.firefly.log.Logger
import org.lwjgl.glfw.Callbacks.glfwFreeCallbacks
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.system.MemoryStack.stackPush
import org.lwjgl.system.MemoryUtil.NULL

typealias Dimensions = Pair<Int, Int>

typealias KeyActionCallback = (Keys, KeyAction) -> Unit

class GLFWScreen private constructor(
    private val title: String,
    private val fullScreen: Boolean,
    private var dimensions: Dimensions
) {
    private val logger = Logger.getLogger<GLFWScreen>()
    private val onKeyActionCallbacks = mutableListOf<KeyActionCallback>()
    private var window: Long = NULL

    init {
        GLFWErrorCallback.createPrint(System.err)
        if (!glfwInit()) throw IllegalStateException("Unable to init GLFW")

        glfwDefaultWindowHints()
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE)
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE)

        val (windowWidth, windowHeight) = dimensions
        this.window = glfwCreateWindow(windowWidth, windowHeight, title, NULL, NULL)

        checkWindowNotNull()

        //On screen key callback declared here to handle key usability
        glfwSetKeyCallback(window) { _, glfwKeyCode, _, glfwActionCode, _ ->
            onKeyActionCallbacks.forEach {
                it.invoke(Keys.fromGlfw(glfwKeyCode), KeyAction.fromGlfw(glfwActionCode))
            }
        }

        glfwSetWindowSizeCallback(window) { _, width, height ->
            dimensions = dimensions.copy(width, height)
        }

        runCatching {
            with(stackPush()) {
                val pWidth = mallocInt(1)
                val pHeight = mallocInt(1)

                glfwGetWindowSize(window, pWidth, pHeight)
                val vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor())
                    ?: throw IllegalStateException("Unable to get video mode")

                glfwSetWindowPos(window, (vidMode.width() - pWidth[0]) / 2, (vidMode.height() - pHeight[0]) / 2)

                if (fullScreen)
                    glfwSetWindowSize(window, vidMode.width(), vidMode.height())
            }
        }.onFailure {
            logger.d(it.message)
        }
    }

    fun showWindow() {
        checkWindowNotNull()
        glfwMakeContextCurrent(window)
        glfwSwapInterval(1)
        glfwShowWindow(window)
    }

    fun notifyDimensionChanged(screenWidth: Int, screenHeight: Int) {
        checkWindowNotNull()
        val (width, height) = dimensions
        if (width != screenWidth || height != screenHeight)
            glfwSetWindowSize(window, width, height)
    }

    fun destroy() {
        checkWindowNotNull()
        glfwFreeCallbacks(window)
        glfwDestroyWindow(window)

        glfwTerminate()
        glfwSetErrorCallback(null)?.free()

        window = NULL
    }

    fun addKeyActionCallback(callback: (Keys, KeyAction) -> Unit) = onKeyActionCallbacks.add(callback)

    fun removeKeyActionCallback(callback: (Keys, KeyAction) -> Unit) = onKeyActionCallbacks.remove(callback)

    private fun checkWindowNotNull() {
        if (window == NULL) throw IllegalStateException("Window is null")
    }

    fun isActive(): Boolean {
        checkWindowNotNull()
        return glfwWindowShouldClose(window)
    }

    fun update() {
        checkWindowNotNull()
        glfwSwapBuffers(window)
        glfwPollEvents()
    }

    class Builder {
        private var title = DEFAULT_TITLE
        private var fullScreen = false
        private var dimensions = Dimensions(DEFAULT_SCREEN_WIDTH, DEFAULT_SCREEN_HEIGHT)

        fun setTitle(title: String) = this.apply { this@Builder.title = title }

        fun setFullscreen(fullScreen: Boolean) = this.apply { this@Builder.fullScreen = fullScreen }

        fun setDimension(width: Int, height: Int) = this.apply {
            if (width != 0 && height != 0)
                this@Builder.dimensions = Dimensions(width, height)
        }

        fun build() = GLFWScreen(title, fullScreen, dimensions)

        companion object {
            private const val DEFAULT_TITLE = "Screen"
            private const val DEFAULT_SCREEN_WIDTH = 640
            private const val DEFAULT_SCREEN_HEIGHT = 480
        }
    }
}