package com.conceptic.firefly.app.gl

import com.conceptic.firefly.app.gl.support.GL
import com.conceptic.firefly.app.gl.support.mat.Matrix4

abstract class GLEntity(val name: String) {
    val modelTransform: FloatArray
        get() = modelMatrix.toFloatArray()
    private val glEntityBufferUtils: GLEntityBufferUtils = GLEntityBufferUtils()
    private val modelMatrix = Matrix4()

    fun create() {
        glEntityBufferUtils.create()
        GL.bindVertexArray(glEntityBufferUtils.vao) {
            createInternal(glEntityBufferUtils)
        }
    }

    fun render() {
        GL.bindVertexArray(glEntityBufferUtils.vao) {
            renderInternal(glEntityBufferUtils)
        }
    }

    protected abstract fun createInternal(glEntityBufferUtils: GLEntityBufferUtils)

    protected abstract fun renderInternal(glEntityBufferUtils: GLEntityBufferUtils)

    fun destroy() {
        glEntityBufferUtils.clear()
        modelMatrix.identity()
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
}