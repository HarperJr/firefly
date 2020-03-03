package com.conceptic.firefly.app.game.camera

import com.conceptic.firefly.app.gl.support.mat.Matrix4
import com.conceptic.firefly.app.gl.support.vec.Vector3

class Camera(
    private val isPerspective: Boolean,
    private val fov: Float = 60f,
    private val near: Float = -0.5f,
    private val far: Float = 0.5f
) {
    var width: Int = 0
        private set
    var height: Int = 0
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

    fun update(width: Int, height: Int) {
        this.width = width
        this.height = height

        projectionMatrix.identity()
        viewMatrix.identity()

        if (isPerspective) {
            setPerspective(width, height)
        } else setOrtho(width, height)
        viewMatrix.translate(position.x, position.y, position.z)
    }

    private fun setPerspective(width: Int, height: Int) {
        val aspect = if (height != 0) width.toFloat() / height.toFloat() else 0f
        projectionMatrix.setPerspective(fov, aspect, near, far)
    }

    private fun setOrtho(width: Int, height: Int) {
        projectionMatrix.setOrtho(0f, width.toFloat(), 0f, height.toFloat(), near, far)
    }
}