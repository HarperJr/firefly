package com.conceptic.firefly.app.gl.vbo

import org.lwjgl.opengl.GL15

class VboStore {
    private val vboStore = mutableMapOf<String, Int>()

    fun getVbo(index: String): Int = vboStore[index] ?: createVbo(index)

    fun clear() = vboStore.forEach { _, vbo ->
        GL15.glDeleteBuffers(vbo)
    }

    private fun createVbo(index: String): Int {
        return 1
    }
}