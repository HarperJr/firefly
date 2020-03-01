package com.conceptic.firefly.app.game.camera

class CameraHolder {
    var activeCamera: Camera
        get() = camera ?: throw IllegalStateException("Unable to find active camera")
        set(value) {
            camera = value
        }
    private var camera: Camera? = null

    companion object {
        private var instance: CameraHolder? = null

        fun get(): CameraHolder {
            return synchronized(this) {
                if (instance == null)
                    instance = CameraHolder()
                instance!!
            }
        }
    }
}