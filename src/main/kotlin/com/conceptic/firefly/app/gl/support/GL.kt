package com.conceptic.firefly.app.gl.support

import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL30

/**
 * Support class is used for make all needed GL invocations to be suitable to kotlin stile
 */
object GL {
    fun bindVertexArray(vertexArray: Int, invocation: () -> Unit) {
        GL30.glBindVertexArray(vertexArray)
        invocation.invoke()
        GL30.glBindVertexArray(0)
    }

    fun bindBufferArray(bufferArray: Int, type: Int, invocation: () -> Unit) {
        GL30.glBindBuffer(type, bufferArray)
        invocation.invoke()
        GL30.glBindBuffer(type, 0)
    }

    fun bufferData(vbo: Int, pointer: Int, size: Int, type: Int, arrayBuffer: IntArray, mode: Int) {
        bindBufferArray(vbo, type) {
            GL30.glBufferData(type, arrayBuffer, mode)
            GL30.glVertexAttribPointer(
                pointer,
                size,
                GL11.GL_UNSIGNED_INT,
                false,
                size * 4,
                0L
            )
        }
    }

    fun bufferData(vbo: Int, pointer: Int, size: Int, type: Int, arrayBuffer: FloatArray, mode: Int) {
        bindBufferArray(vbo, type) {
            GL30.glBufferData(type, arrayBuffer, mode)
            GL30.glVertexAttribPointer(
                pointer,
                size,
                GL11.GL_FLOAT,
                false,
                size * 4,
                0L
            )
        }
    }

    fun bufferData(vbo: Int, type: Int, pointer: Int, size: Int, arrayBuffer: DoubleArray, mode: Int) {
        bindBufferArray(vbo, type) {
            GL30.glBufferData(type, arrayBuffer, mode)
            GL30.glVertexAttribPointer(
                pointer,
                size,
                GL11.GL_DOUBLE,
                false,
                size * 8,
                0L
            )
        }
    }
}
