package com.conceptic.firefly.app.camera

import com.conceptic.firefly.app.gl.support.Vector3
import com.conceptic.firefly.utils.MatrixStackHolder

class Camera(private val cameraSettings: CameraSettings) {
    private var position = Vector3.ZERO

    fun move(vector: Vector3) {
        position = vector
    }

    fun update(width: Int, height: Int) {
        if (cameraSettings.isPerspective) {
            val aspect = if (height != 0) width.toFloat() / height.toFloat() else 0f
            MatrixStackHolder.setPerspective(cameraSettings.fov, aspect, cameraSettings.zNear, cameraSettings.zFar)
        } else MatrixStackHolder.setOrtho(0f, width.toFloat(), 0f, height.toFloat(), cameraSettings.zNear, cameraSettings.zFar)

        MatrixStackHolder.translate(position.x, position.y, position.z)
    }

    class CameraSettings private constructor(
        val isPerspective: Boolean,
        val zNear: Float,
        val zFar: Float,
        val fov: Float
    ) {
        object Builder {
            private var isPerspective = false
            private var zNear = -1000f
            private var zFar = 1000f
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