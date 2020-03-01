package com.conceptic.firefly.app.gl

import com.conceptic.firefly.app.gl.support.mat.Matrix4
import org.lwjgl.opengl.GL30

abstract class GLEntity(val name: String) {
    val model: FloatArray
        get() = modelMatrix.toFloatArray()

    private var vao: Int = -1
    private val vboList = mutableListOf<Int>()

    private val modelMatrix = Matrix4()

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

            modelMatrix.identity()
        } else {
            throw IllegalStateException("GLEntity $name is not loaded yet. Please create this before binding")
        }
    }

    fun translate(x: Float, y: Float, z: Float) {
        modelMatrix.translate(x, y, z)
    }

    fun rotate(a: Float, x: Float, y: Float, z: Float) {
        modelMatrix.rotate(a, x, y, z)
    }

    fun scale(x: Float, y: Float, z: Float) {
        modelMatrix.scale(x, y, z)
    }

    protected fun createVbo(): Int {
        return GL30.glGenBuffers()
            .also { vbo -> vboList.add(vbo) }
    }

    protected abstract fun loadInternal()
}