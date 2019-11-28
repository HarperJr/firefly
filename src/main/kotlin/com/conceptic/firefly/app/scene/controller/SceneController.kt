package com.conceptic.firefly.app.scene.controller

import com.conceptic.firefly.app.scene.Scene

abstract class SceneController(
    private val scene: Scene
) {
    fun init() {

    }

    abstract fun update()

    abstract fun fixedUpdate()

    abstract fun destroy()
}