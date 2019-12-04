package com.conceptic.firefly.app.scene

import com.conceptic.firefly.app.scene.obj.VesselObject

class MainScene(sceneDispatcher: SceneDispatcher) : Scene(sceneDispatcher) {
    override fun retainSelfInstance(): Boolean = false

    override fun onCreate() {
        super.onCreate()

        instansiate(VesselObject())
    }

    override fun onDestroy() {

    }
}