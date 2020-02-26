package com.conceptic.firefly.utils

import org.joml.Matrix4f
import org.lwjgl.BufferUtils
import java.nio.FloatBuffer
import java.util.*


object MatrixStackHolder {
    private val matrixStack = ArrayDeque<Matrix4f>()

    private val pMatrix = Matrix4f()
    private val identityMatrix = Matrix4f()

    private val pMatrixBuffer = FloatArray(16)
    private val mvMatrixBuffer = FloatArray(16)

    val projectionMatrixAsBuffer: FloatArray
        get() {
            return pMatrix.get(pMatrixBuffer)
        }

    val modelViewMatrixAsBuffer: FloatArray
        get() {
            var mat = identityMatrix
            if (!matrixStack.isEmpty()) mat = matrixStack.peek()
            return mat.get(mvMatrixBuffer)
        }

    fun identity() {
        pMatrix.identity()
    }

    fun setPerspective(fov: Float, aspect: Float, near: Float, far: Float) {
        pMatrix.perspective(fov, aspect, near, far)
    }

    fun setOrtho(left: Float, right: Float, bottom: Float, top: Float, near: Float, far: Float) {
        pMatrix.ortho(left, right, bottom, top, near, far)
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
