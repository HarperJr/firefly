package com.conceptic.firefly.utils

import org.joml.Matrix4f
import org.lwjgl.BufferUtils
import java.nio.FloatBuffer
import java.util.*


object MatrixStackHolder {
    private val matrixStack = ArrayDeque<Matrix4f>()

    private val pMatrix = Matrix4f()
    private val identityMatrix = Matrix4f()

    private val pMatrixBuffer = BufferUtils.createFloatBuffer(16)
    private val mvMatrixBuffer = BufferUtils.createFloatBuffer(16)

    val projectionMatrixAsBuffer: FloatBuffer
        get() {
            pMatrixBuffer.clear()
            return pMatrix.get(pMatrixBuffer)
        }

    val modelViewMatrixAsBuffer: FloatBuffer
        get() {
            mvMatrixBuffer.clear()
            var mat = identityMatrix
            if (!matrixStack.isEmpty()) mat = matrixStack.peek()
            return mat.get(mvMatrixBuffer)
        }

    fun setPerspective(fov: Float, aspect: Float, near: Float, far: Float) {
        val ratio = 1f / Math.tan(Math.toRadians((fov / 2f).toDouble())).toFloat()
        val yScale = ratio * aspect
        val frustum = far - near

        pMatrix.zero()

        pMatrix.m00(ratio)
        pMatrix.m11(yScale)
        pMatrix.m22(-(far + near) / frustum)
        pMatrix.m23(-1f)
        pMatrix.m32(-(2f * near * far) / frustum)
    }

    fun setOrtho(left: Float, right: Float, bottom: Float, top: Float, near: Float, far: Float) {
        val yScale = 2f / (right - left)
        val xScale = 2f / (top - bottom)
        val frustum = -2f / (far - near)

        pMatrix.zero()

        pMatrix.m00(xScale)
        pMatrix.m11(yScale)
        pMatrix.m22(frustum)
        pMatrix.m30(-(right + left) / (right - left))
        pMatrix.m31((top + bottom) / (top - bottom))
        pMatrix.m32((far + near) / (far - near))
        pMatrix.m33(1f)
    }

    fun pushMatrix() {
        val mat = Matrix4f()
        if (!matrixStack.isEmpty()) {
            mat.set(matrixStack.peek())
        }
        matrixStack.add(mat)
    }

    fun popMatrix() {
        if (matrixStack.isEmpty()) return
        matrixStack.remove()
    }

    fun translate(i: Float, j: Float, k: Float) {
        if (matrixStack.isEmpty())
            matrixStack.add(identityMatrix)
        matrixStack.peek().translate(i, j, k)
    }

    fun rotate(a: Float, i: Float, j: Float, k: Float) {
        if (matrixStack.isEmpty())
            matrixStack.add(identityMatrix)
        matrixStack.peek().rotate(a, i, j, k)
    }

    fun scale(i: Float, j: Float, k: Float) {
        if (matrixStack.isEmpty())
            matrixStack.add(identityMatrix)
        matrixStack.peek().scale(i, j, k)
    }
}
