package com.conceptic.firefly.app.scene

import com.conceptic.firefly.app.scene.obj.SceneObject

abstract class Scene(
    private val sceneDispatcher: SceneDispatcher
) {
    abstract fun retainSelfInstance(): Boolean

    open fun onCreate() {

    }

    open fun onDestroy() {

    }

    open fun onFixedUpdate() {

    }

    open fun onUpdate() {

    }

    protected fun instansiate(obj: SceneObject) = sceneDispatcher.addObj(obj)

    protected fun annihilate(obj: SceneObject) = sceneDispatcher.removeObj(obj)
}