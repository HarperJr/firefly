package com.conceptic.firefly.app.gl.renderer.factory

import com.conceptic.firefly.app.gl.mesh.Mesh
import com.conceptic.firefly.app.gl.renderer.Renderer

interface RendererFactory {
    fun meshRenderer(): Renderer<Mesh>
}