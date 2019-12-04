package com.conceptic.firefly.app.scene.obj

abstract class SceneObject(val resource: String) {
    abstract fun retainSelfInstance(): Boolean

    open fun onDestroy() {

    }

    open fun onCreate() {}

    open fun onUpdate() {}

    open fun onFixedUpdate() {}
}