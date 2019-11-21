package com.conceptic.firefly.app.gl.renderer.factory.mesh

import com.conceptic.firefly.app.gl.mesh.Mesh
import com.conceptic.firefly.app.gl.renderer.Renderer
import com.conceptic.firefly.app.gl.renderer.factory.RendererFactory
import com.conceptic.firefly.app.gl.shader.ShaderStore

class MeshRendererFactory(
    private val shaderStore: ShaderStore
) : RendererFactory {
    override fun meshRenderer(): Renderer<Mesh> = MeshRenderer()
}