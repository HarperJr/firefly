package com.conceptic.firefly.app.gl.mesh

import com.conceptic.firefly.app.gl.renderer.Renderable

abstract class Mesh(val name: String) : Renderable {
    abstract val uniqueIndex: String
}