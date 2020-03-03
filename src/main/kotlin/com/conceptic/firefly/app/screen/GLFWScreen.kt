package com.conceptic.firefly.app.screen

import com.conceptic.firefly.app.screen.listener.KeysListener
import com.conceptic.firefly.app.screen.listener.MouseListener
import com.conceptic.firefly.app.screen.listener.ScreenListener
import com.conceptic.firefly.log.Logger
import org.lwjgl.glfw.Callbacks.glfwFreeCallbacks
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.GL_TRUE
import org.lwjgl.system.MemoryStack.stackPush
import org.lwjgl.system.MemoryUtil.NULL

class GLFWScreen(
    screenWidth: Int,
    screenHeight: Int,
    private val title: String,
    private val fullScreen: Boolean
) {
    var screenListener: ScreenListener? = null
    var mouseListener: MouseListener? = null
    var keysListener: KeysListener? = null

    private val logger = Logger.getLogger<GLFWScreen>()
    private var window: Long = NULL

    var width: Int = screenWidth
        private set
    var height: Int = screenHeight
        private set

    private var lastCursorXPos: Float = -1f
    private var lastCursorYPost: Float = -1f

    fun show() {
        GLFWErrorCallback.createPrint(System.err)
        if (!glfwInit()) throw IllegalStateException("Unable to init GLFW")

        glfwDefaultWindowHints()
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE)
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE)
        glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, GL_TRUE)

        this.window = glfwCreateWindow(width, height, title, NULL, NULL)

        checkWindowNotNull()

        //On screen key callback declared here to handle key usability
        glfwSetKeyCallback(window) { _, keyCode, _, actionCode, _ ->
            val key = Key.fromGlfw(keyCode)
            when (actionCode) {
                GLFW_PRESS -> keysListener?.onPressed(key)
                GLFW_RELEASE -> keysListener?.onReleased(key)
            }
        }

        glfwSetCursorPosCallback(window) { _, x, y ->
            this.lastCursorXPos = x.toFloat()
            this.lastCursorYPost = height - y.toFloat()
            mouseListener?.onMoved(this.lastCursorXPos, this.lastCursorYPost)
        }

        glfwSetMouseButtonCallback(window) { _, button, action, _ ->
            when (action) {
                GLFW_RELEASE ->
                    if (button == GLFW_MOUSE_BUTTON_LEFT)
                        mouseListener?.onClicked(this.lastCursorXPos, this.lastCursorYPost)
                GLFW_PRESS -> if (button == GLFW_MOUSE_BUTTON_LEFT)
                    mouseListener?.onPressed(this.lastCursorXPos, this.lastCursorYPost)
            }
        }

        glfwSetWindowSizeCallback(window) { _, newWidth, newHeight ->
            if (width != newWidth || height != newHeight) {
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
                    ?: throw IllegalStateException("Unable to newInstance video mode")

                glfwSetWindowPos(window, (vidMode.width() - pWidth[0]) / 2, (vidMode.height() - pHeight[0]) / 2)

                if (fullScreen)
                    glfwSetWindowSize(window, vidMode.width(), vidMode.height())
            }
        }.onFailure {
            logger.debug(it.message)
        }

        glfwMakeContextCurrent(window)
        glfwSwapInterval(1)
        glfwShowWindow(window)

        GL.createCapabilities()
        screenListener?.onShow()
    }

    fun destroy() {
        screenListener?.onDestroy()
        checkWindowNotNull()

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
        screenListener?.onUpdate()
        glfwSwapBuffers(window)
        glfwPollEvents()
    }
}