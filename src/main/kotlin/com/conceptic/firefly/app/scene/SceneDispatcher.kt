package com.conceptic.firefly.app.scene

import com.conceptic.firefly.app.scene.obj.SceneObject
import org.koin.core.KoinComponent

class SceneDispatcher : KoinComponent {
    private var currentScene: SceneWrapper? = null
    private var sceneObjects = mutableMapOf<String, MutableList<SceneObject>>()

    fun addObj(sceneObject: SceneObject) {
        currentScene?.let { scene ->
            val objectsPool = sceneObjects.getOrPut(scene.name) { mutableListOf() }
            if (!objectsPool.contains(sceneObject)) {
                if (objectsPool.add(sceneObject))
                    sceneObject.onCreate()
            }
        }
    }

    fun removeObj(sceneObject: SceneObject) {
        currentScene?.let { scene ->
            sceneObjects[scene.name]?.remove(sceneObject)
        }
    }

    fun resetCurrent(scene: Scene) {
        val sceneWrapper = SceneWrapper(scene, this)
        currentScene?.let { scene ->
            scene.onDestroy()
            if (!scene.retainSelfInstance()) {
                val objectsPool = sceneObjects[scene.name]
                objectsPool?.forEach {
                    if (!it.retainSelfInstance()) {
                        it.onDestroy()
                        objectsPool.remove(it)
                    }
                }
            }
        }

        currentScene = sceneWrapper.also { it.onCreate() }
    }

    fun dispatchCurrent(): Scene? {
        return currentScene?.also { scene ->
            sceneObjects[scene.name]?.forEach {
                it.onUpdate()
            }
            scene.onUpdate()
        }
    }
}