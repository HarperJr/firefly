package com.conceptic.firefly.app.scene

abstract class Scene {
    abstract fun onCreate()

    abstract fun onDestroy()

    open fun onFixedUpdate() {}

    open fun onUpdate() {}
}