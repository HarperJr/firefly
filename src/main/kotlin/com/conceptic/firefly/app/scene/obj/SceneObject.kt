package com.conceptic.firefly.app.scene.obj

abstract class SceneObject {
    abstract fun onCreate()

    abstract fun onDestroy()

    abstract fun retainSelfInstance(): Boolean

    open fun onUpdate() {}

    open fun onFixedUpdate() {}
}