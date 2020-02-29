package com.conceptic.firefly.app.gl

import org.lwjgl.opengl.GL30

abstract class GLEntity(val name: String) {
    private var vao: Int = -1
    private val vboList = mutableListOf<Int>()

    fun create() {
        vao = GL30.glGenVertexArrays()
        bind {
            loadInternal()
        }
    }

    fun destroy() {
        for (vbo in vboList)
            GL30.glDeleteBuffers(vbo)
        GL30.glDeleteVertexArrays(vao)
    }

    fun bind(invocation: () -> Unit) {
        if (vao != -1) {
            GL30.glBindVertexArray(vao)
            invocation.invoke()
            GL30.glBindVertexArray(0)
        } else throw IllegalStateException("GLEntity $name is not loaded yet. Please create this before binding")
    }

    protected fun createVbo(): Int {
        return GL30.glGenBuffers()
            .also { vbo -> vboList.add(vbo) }
    }

    protected abstract fun loadInternal()
}