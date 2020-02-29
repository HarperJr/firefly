package com.conceptic.firefly.app.gl.support.mat

import com.conceptic.firefly.app.gl.support.vec.Vector3
import org.joml.Matrix4f

class Matrix4 {
    private val floatArray = FloatArray(16)
    private val matrix = Matrix4f()

    fun identity() = matrix.identity()

    fun toFloatArray(): FloatArray = matrix.get(floatArray)

    fun setOrtho(left: Float, right: Float, bottom: Float, top: Float, near: Float, far: Float) {
        matrix.ortho(left, right, bottom, top, near, far)
    }

    fun setPerspective(fov: Float, aspect: Float, near: Float, far: Float) {
        matrix.perspective(fov, aspect, near, far)
    }

    fun rotate(a: Float, x: Float, y: Float, z: Float) {
        matrix.rotate(a, x, y, z)
    }

    fun translate(x: Float, y: Float, z: Float) {
        matrix.translate(x, y, z)
    }

    fun translate(vec: Vector3) {
        translate(vec.x, vec.y, vec.z)
    }

    fun scale(x: Float, y: Float, z: Float) {
        matrix.scale(x, y, z)
    }

    fun scale(vec: Vector3) {
        scale(vec.x, vec.y ,vec.z)
    }
}