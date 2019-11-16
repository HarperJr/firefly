package com.conceptic.firefly.app.gl.mesh

import com.conceptic.firefly.app.gl.renderer.Renderer

interface MeshFactory {
    fun withRenderer(renderer: Renderer): MeshFactory

    fun produce(): Mesh
}