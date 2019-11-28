package com.conceptic.firefly.app.scene

import com.conceptic.firefly.app.gl.renderer.Renderable

abstract class Scene: Renderable {
    abstract fun onCreate()

    abstract fun onDestroy()

    abstract fun retainSelfInstance(): Boolean

    open fun onFixedUpdate() {

    }

    open fun onUpdate() {

    }
}