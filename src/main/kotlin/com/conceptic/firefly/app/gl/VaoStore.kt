package com.conceptic.firefly.app.gl

import org.lwjgl.opengl.GL30

class VaoStore {
    private val vaoStore = mutableMapOf<String, Int>()

    fun getVao(index: String): Int = vaoStore[index] ?: createVao(index)

    fun clear() = vaoStore.forEach { _, vao ->
        GL30.glDeleteVertexArrays(vao)
    }

    private fun createVao(index: String): Int {
        return GL30.glGenVertexArrays().also { vao -> vaoStore[index] = vao }
    }
}
