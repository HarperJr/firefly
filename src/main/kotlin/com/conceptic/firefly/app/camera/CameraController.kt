package com.conceptic.firefly.app.camera

import com.conceptic.firefly.app.gl.support.Vector3
import com.conceptic.firefly.screen.support.ScreenUpdatesPublisher
import com.conceptic.firefly.screen.support.ScreenUpdatesSubscriberAdapter

class CameraController {
    var cameraSettings: CameraSettings = CameraSettings.DEFAULT
        set(value) {
            field = value
            camera.setSettings(value.isPerspectiveProjection, value.zNear, value.zFar, value.fov)
        }

    private val camera = Camera()
    private var currentWidth = 0
    private var currentHeight = 0

    fun move(vector3: Vector3) = camera.move(vector3)

    fun update() {
        camera.update(currentWidth, currentHeight)
    }

    fun changeProjectionSize(width: Int, height: Int) {
        this.currentWidth = width
        this.currentWidth = height
    }

    class CameraSettings private constructor(
        val isPerspectiveProjection: Boolean,
        val zNear: Float,
        val zFar: Float,
        val fov: Float
    ) {
        object Builder {
            private var isPerspectiveProjection = false
            private var zNear = -1000f
            private var zFar = 1000f
            private var fov = 60f

            fun isPerspectiveProjection(isPerspectiveProjection: Boolean) =
                this.apply { this.isPerspectiveProjection = isPerspectiveProjection }

            fun zArguments(zNear: Float, zFar: Float) = this.apply {
                this.zNear = zNear
                this.zFar = zFar
            }

            fun ratio(ratio: Float) = this.also { this.fov = ratio }

            fun build() = CameraSettings(isPerspectiveProjection, zNear, zFar, fov)
        }

        companion object {
            val DEFAULT = Builder.build()
        }
    }
}