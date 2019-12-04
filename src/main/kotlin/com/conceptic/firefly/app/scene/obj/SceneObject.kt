package com.conceptic.firefly.app.scene.obj

import com.conceptic.firefly.app.scene.Scene
import com.conceptic.firefly.app.scene.res.Resource
import java.lang.ref.WeakReference

abstract class SceneObject(val objResource: Resource) {
    private var sceneRef: WeakReference<Scene>? = null

    abstract fun retainSelfInstance(): Boolean

    fun onInstantiate(scene: Scene) {
        sceneRef = WeakReference(scene)
    }

    open fun onDestroy() {}

    open fun onCreate() {}

    open fun onUpdate() {}

    open fun onFixedUpdate() {}
}