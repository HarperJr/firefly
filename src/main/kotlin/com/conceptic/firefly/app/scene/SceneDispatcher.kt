package com.conceptic.firefly.app.scene

import com.conceptic.firefly.app.scene.obj.SceneObject

class SceneDispatcher {
    private var currentScene: Scene? = null
    private var sceneObjects = mutableMapOf<String, MutableList<SceneObject>>()

    fun addObj(sceneObject: SceneObject) {
        currentScene?.let { scene ->
            val objectsPool = sceneObjects.getOrPut(resolveName(scene)) { mutableListOf() }
            if (!objectsPool.contains(sceneObject)) {
                if (objectsPool.add(sceneObject))
                    sceneObject.onCreate()
            }
        }
    }

    fun removeObj(sceneObject: SceneObject) {
        currentScene?.let { scene ->
            sceneObjects[resolveName(scene)]?.remove(sceneObject)
        }
    }

    fun resetCurrent(scene: Scene) {
        currentScene?.let { scene ->
            val key = resolveName(scene)
            scene.onDestroy()
            if (!scene.retainSelfInstance()) {
                val objectsPool = sceneObjects[key]
                objectsPool?.forEach {
                    if (!it.retainSelfInstance()) {
                        it.onDestroy()
                        objectsPool.remove(it)
                    }
                }
            }
        }

        currentScene = scene.also {
            it.onCreate()
        }
    }

    fun dispatchCurrent(): Scene? {
        return currentScene?.also { scene ->
            sceneObjects[resolveName(scene)]?.forEach {
                it.onUpdate()
            }
            scene.onUpdate()
        }
    }

    private fun resolveName(scene: Scene): String = scene::class.simpleName
        ?: throw IllegalArgumentException("Invalid scene instance: $scene")
}