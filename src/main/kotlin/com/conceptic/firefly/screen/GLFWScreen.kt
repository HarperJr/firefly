package com.conceptic.firefly.screen

import com.conceptic.firefly.log.Logger
import org.lwjgl.glfw.Callbacks.glfwFreeCallbacks
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.system.MemoryStack.stackPush
import org.lwjgl.system.MemoryUtil.NULL

typealias Dimensions = Pair<Int, Int>

class GLFWScreen(
    title: String,
    screenWidth: Int,
    screenHeight: Int,
    private val fullScreen: Boolean,
    private val keyActionListener: KeyActionListener,
    private val screenActionListener: ScreenActionListener,
    private val mouseActionListener: MouseActionListener
) {
    private val logger = Logger.getLogger<GLFWScreen>()
    private var window: Long = NULL

    var width: Int = screenWidth
        private set
    var height: Int = screenHeight
        private set

    var lastCursorXPos: Int = -1
    var lastCursorYPost: Int = -1

    init {
        screenActionListener.onInit()

        GLFWErrorCallback.createPrint(System.err)
        if (!glfwInit()) throw IllegalStateException("Unable to init GLFW")

        glfwDefaultWindowHints()
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE)
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE)

        this.window = glfwCreateWindow(width, height, title, NULL, NULL)

        checkWindowNotNull()

        //On screen key callback declared here to handle key usability
        glfwSetKeyCallback(window) { _, keyCode, _, actionCode, _ ->
            val key = Key.fromGlfw(keyCode)
            when (actionCode) {
                GLFW_PRESS -> keyActionListener.onPressed(key)
                GLFW_RELEASE -> keyActionListener.onReleased(key)
            }
        }

        glfwSetCursorPosCallback(window) { _, x, y ->
            this.lastCursorXPos = x.toInt()
            this.lastCursorYPost = y.toInt()
            mouseActionListener.onMoved(this.lastCursorXPos, this.lastCursorYPost)
        }

        glfwSetMouseButtonCallback(window) { _, button, action, _ ->
            when (action) {
                GLFW_RELEASE -> mouseActionListener.onClicked(this.lastCursorXPos, this.lastCursorYPost)
                GLFW_REPEAT -> mouseActionListener.onDoubleClicked(this.lastCursorXPos, this.lastCursorYPost)
            }
        }

        glfwSetWindowSizeCallback(window) { _, newWidth, newHeight ->
            if (width != newWidth || height != newHeight) {
                screenActionListener.onSizeChanged(width, height)
                this.width = newWidth
                this.height = newHeight
            }
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
            logger.debug(it.message)
        }

        glfwMakeContextCurrent(window)
        glfwSwapInterval(1)
    }

    fun showWindow() {
        checkWindowNotNull()
        screenActionListener.onShow()

        glfwShowWindow(window)
    }

    fun destroy() {
        checkWindowNotNull()
        screenActionListener.onDestroy()

        glfwFreeCallbacks(window)
        glfwDestroyWindow(window)

        glfwTerminate()
        glfwSetErrorCallback(null)?.free()

        window = NULL
    }

    private fun checkWindowNotNull() {
        if (window == NULL) throw IllegalStateException("Window is null")
    }

    fun isActive(): Boolean {
        checkWindowNotNull()
        return !glfwWindowShouldClose(window)
    }

    fun update() {
        checkWindowNotNull()
        screenActionListener.onUpdate()

        glfwSwapBuffers(window)
        glfwPollEvents()
    }
}