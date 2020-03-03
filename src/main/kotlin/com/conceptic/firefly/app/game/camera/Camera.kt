package com.conceptic.firefly.app.game.camera

import com.conceptic.firefly.app.gl.support.mat.Matrix4
import com.conceptic.firefly.app.gl.support.vec.Vector3

class Camera(
    private val isPerspective: Boolean,
    private val fov: Float = 60f,
    private val near: Float = -0.5f,
    private val far: Float = 0.5f
) {
    var width: Float = 0f
        private set
    var height: Float = 0f
        private set
    val projection: FloatArray
        get() = projectionMatrix.toFloatArray()
    val view: FloatArray
        get() = viewMatrix.toFloatArray()

    private val projectionMatrix = Matrix4()
    private val viewMatrix = Matrix4()

    private val position = Vector3.ZERO

    fun move(vector: Vector3) {
        position.translate(vector)
    }

    fun setPosition(vector: Vector3) {
        position.set(vector)
    }

    fun update(width: Float, height: Float) {
        this.width = width
        this.height = height

        projectionMatrix.identity()
        if (isPerspective) {
            setPerspective(width, height)
        } else setOrtho(width, height)

        viewMatrix.identity()
        viewMatrix.translate(position.x, position.y, position.z)
    }

    private fun setPerspective(width: Float, height: Float) {
        val aspect = if (height != 0f) width / height else 0f
        projectionMatrix.setPerspective(fov, aspect, near, far)
    }

    private fun setOrtho(width: Float, height: Float) {
        projectionMatrix.setOrtho(0f, width, 0f, height, near, far)
    }
}