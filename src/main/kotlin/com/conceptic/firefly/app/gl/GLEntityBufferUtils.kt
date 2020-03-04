package com.conceptic.firefly.app.gl

import org.lwjgl.opengl.GL30

class GLEntityBufferUtils {
    val vao: Int
        get() {
            return if (innerVao == -1) {
                throw IllegalArgumentException("Vao is not initialized")
            } else innerVao
        }
    private var innerVao: Int = -1
    private val vboList = mutableListOf<Int>()

    fun create() {
        if (innerVao == -1) {
            innerVao = GL30.glGenVertexArrays()
        }
    }

    fun createVbo(): Int {
        return GL30.glGenBuffers()
            .also { vboList.add(it) }
    }

    fun clear() {
        for (vbo in vboList)
            GL30.glDeleteBuffers(vbo)
        GL30.glDeleteVertexArrays(vao)
        innerVao = -1
    }
}
