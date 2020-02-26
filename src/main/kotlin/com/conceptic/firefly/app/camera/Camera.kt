package com.conceptic.firefly.app.camera

import com.conceptic.firefly.app.gl.support.Vector3
import org.joml.Matrix4f

class Camera(private val cameraSettings: CameraSettings) {
    val projection: FloatArray
        get() = projectionMatrix.get(projectionArray)
    val view: FloatArray
        get() = viewMatrix.get(viewArray)

    // Arrays are used in order to upload matrices into them
    private val projectionArray = FloatArray(16)
    private val viewArray = FloatArray(16)

    private val projectionMatrix = Matrix4f()
    private val viewMatrix = Matrix4f()

    private val position = Vector3.ZERO

    fun move(vector: Vector3) {
        position.translate(vector)
    }

    fun setPosition(vector: Vector3) {
        position.set(vector)
    }

    fun update(width: Int, height: Int) {
        projectionMatrix.identity()
        if (cameraSettings.isPerspective) {
            val aspect = if (height != 0) width.toFloat() / height.toFloat() else 0f
            projectionMatrix.setPerspective(cameraSettings.fov, aspect, cameraSettings.zNear, cameraSettings.zFar)
        } else projectionMatrix.setOrtho(
            0f,
            width.toFloat(),
            0f,
            height.toFloat(),
            cameraSettings.zNear,
            cameraSettings.zFar
        )
        viewMatrix.translate(position.x, position.y, position.z)
    }

    class CameraSettings private constructor(
        val isPerspective: Boolean,
        val zNear: Float,
        val zFar: Float,
        val fov: Float
    ) {
        object Builder {
            private var isPerspective = false
            private var zNear = -10f
            private var zFar = 10f
            private var fov = 60f

            fun isPerspective(isPerspective: Boolean) =
                this.apply { this.isPerspective = isPerspective }

            fun zArguments(zNear: Float, zFar: Float) = this.apply {
                this.zNear = zNear
                this.zFar = zFar
            }

            fun fov(fov: Float) = this.also { this.fov = fov }

            fun build() = CameraSettings(isPerspective, zNear, zFar, fov)
        }

        companion object {
            val DEFAULT = Builder.build()
        }
    }
}