package com.conceptic.firefly.app.scene

import com.conceptic.firefly.app.scene.obj.SceneObject

class SceneWrapper(
    private val scene: Scene,
    private val sceneDispatcher: SceneDispatcher
) : Scene() {
    override val name: String = scene.name
    private var state: SceneState = SceneState.CREATED

    override fun retainSelfInstance(): Boolean = scene.retainSelfInstance()

    fun create() {

    }

    fun addObj(sceneObject: SceneObject) {
        if (state == SceneState.RESUMED) {
            sceneDispatcher.addObj(sceneObject)
        }
    }

    fun removeObj(sceneObject: SceneObject) {
        if (state == SceneState.RESUMED) {
            sceneDispatcher.removeObj(sceneObject)
        }
    }

    fun removeObj() {

    }
}