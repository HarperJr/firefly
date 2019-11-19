package com.conceptic.firefly.app.gl.mesh.factory

import com.conceptic.firefly.app.gl.mesh.Mesh
import com.conceptic.firefly.app.gl.mesh.SolidMesh
import com.conceptic.firefly.app.gl.renderer.Renderer

class SolidMeshFactory : MeshFactory {
    override fun withRenderer(renderer: Renderer): MeshFactory = this.apply {

    }

    override fun produce(): Mesh {
        return SolidMesh()
    }
}