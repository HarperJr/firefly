package com.conceptic.firefly.app.game

import com.conceptic.firefly.app.game.camera.Camera

abstract class SceneEntity {
    abstract val camera: Camera

    abstract fun create()

    abstract fun update()

    abstract fun destroy()
}
