package com.conceptic.firefly.app.gl.mesh

import com.conceptic.firefly.app.gl.renderer.Renderer

class SolidMeshFactory : MeshFactory {
    override fun withRenderer(renderer: Renderer): MeshFactory = this.apply {

    }

    override fun produce(): Mesh {
        return SolidMesh()
    }
}