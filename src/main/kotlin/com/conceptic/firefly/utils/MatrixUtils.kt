package com.conceptic.firefly.utils

import org.joml.Matrix4f
import org.lwjgl.BufferUtils
import java.nio.FloatBuffer
import java.util.*


object MatrixUtils {
    private val matrixStack = ArrayDeque<Matrix4f>()
    private val projectionMatrix = Matrix4f()
    private val identityMatrix = Matrix4f()
    private val projectionMatrixBuffer = BufferUtils.createFloatBuffer(16)
    private val modelViewMatrixBuffer = BufferUtils.createFloatBuffer(16)

    val projectionMatrixAsBuffer: FloatBuffer
        get() {
            projectionMatrixBuffer.clear()
            return projectionMatrix.get(projectionMatrixBuffer)
        }

    val modelViewMatrixAsBuffer: FloatBuffer
        get() {
            modelViewMatrixBuffer.clear()
            var mat = identityMatrix
            if (!matrixStack.isEmpty()) mat = matrixStack.peek()
            return mat.get(modelViewMatrixBuffer)
        }

    fun setPerspective(fov: Float, aspect: Float, near: Float, far: Float) {
        val ratio = 1f / Math.tan(Math.toRadians((fov / 2f).toDouble())).toFloat()
        val yScale = ratio * aspect
        val frustum = far - near

        projectionMatrix.zero()

        projectionMatrix.m00(ratio)
        projectionMatrix.m11(yScale)
        projectionMatrix.m22(-(far + near) / frustum)
        projectionMatrix.m23(-1f)
        projectionMatrix.m32(-(2f * near * far) / frustum)
    }

    fun setOrtho(left: Float, right: Float, bottom: Float, top: Float, near: Float, far: Float) {
        val yScale = 2f / (right - left)
        val xScale = 2f / (top - bottom)
        val frustum = -2f / (far - near)

        projectionMatrix.zero()

        projectionMatrix.m00(xScale)
        projectionMatrix.m11(yScale)
        projectionMatrix.m22(frustum)
        projectionMatrix.m30(-(right + left) / (right - left))
        projectionMatrix.m31((top + bottom) / (top - bottom))
        projectionMatrix.m32((far + near) / (far - near))
        projectionMatrix.m33(1f)
    }

    fun pushMatrix() {
        val mat = Matrix4f()
        if (!matrixStack.isEmpty()) {
            mat.set(matrixStack.peek())
        }

        addMatrixInStack(mat)
    }

    fun popMatrix() {
        if (matrixStack.isEmpty()) return
        matrixStack.remove()
    }

    fun translate(i: Float, j: Float, k: Float) {
        matrixStack.peek().translate(i, j, k)
    }

    fun rotate(a: Float, i: Float, j: Float, k: Float) {
        matrixStack.peek().rotate(a, i, j, k)
    }

    fun scale(i: Float, j: Float, k: Float) {
        matrixStack.peek().scale(i, j, k)
    }

    private fun addMatrixInStack(mat: Matrix4f) {
        matrixStack.add(mat)
    }
}
