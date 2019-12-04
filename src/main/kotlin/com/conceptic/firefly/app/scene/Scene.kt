package com.conceptic.firefly.app.scene

abstract class Scene {
    abstract val name: String

    abstract fun retainSelfInstance(): Boolean

    open fun onCreate() {

    }

    open fun onDestroy() {

    }

    open fun onFixedUpdate() {

    }

    open fun onUpdate() {

    }
}