package com.conceptic.firefly.app.gl.view

import com.conceptic.firefly.app.gl.renderer.Renderable
import com.conceptic.firefly.app.gl.support.Vector2
import com.conceptic.firefly.app.gl.support.Vector3
import com.conceptic.firefly.app.gl.support.Vector4

abstract class View : Renderable {
    abstract val vertices: List<Vector2>
    abstract val colors: List<Vector4>

    override val isOptimized: Boolean
        get() = false
}