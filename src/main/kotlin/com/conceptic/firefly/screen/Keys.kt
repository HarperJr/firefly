package com.conceptic.firefly.screen

import org.lwjgl.glfw.GLFW

/**
 * All used keys are declared here
 */
enum class Keys {
    ESC, WHITESPACE, A, W, D, S, UNDEFINED;

    companion object {
        fun fromGlfw(key: Int) = when (key) {
            GLFW.GLFW_KEY_ESCAPE -> ESC
            else -> UNDEFINED
        }
    }
}