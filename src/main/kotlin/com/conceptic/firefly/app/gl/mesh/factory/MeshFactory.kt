package com.conceptic.firefly.app.gl.mesh.factory

import com.conceptic.firefly.app.gl.mesh.Mesh
import com.conceptic.firefly.app.gl.renderer.Renderer

interface MeshFactory {
    fun withRenderer(renderer: Renderer): MeshFactory

    fun produce(): Mesh
}