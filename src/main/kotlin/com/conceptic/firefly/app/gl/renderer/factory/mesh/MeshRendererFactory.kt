package com.conceptic.firefly.app.gl.renderer.factory.mesh

import com.conceptic.firefly.app.gl.mesh.Mesh
import com.conceptic.firefly.app.gl.renderer.Renderer
import com.conceptic.firefly.app.gl.renderer.factory.RendererFactory
import com.conceptic.firefly.app.gl.shader.Shader
import com.conceptic.firefly.app.gl.shader.ShaderStore
import com.conceptic.firefly.app.gl.shader.definition.MeshShaderDefinition
import com.conceptic.firefly.app.gl.shader.resolver.ShaderResolver
import com.conceptic.firefly.app.gl.vao.VaoStore

class MeshRendererFactory(
    private val shaderStore: ShaderStore,
    private val vaoStore: VaoStore
) : RendererFactory<Mesh> {
    private val shaderResolver = object : ShaderResolver<Mesh> {
        override fun resolve(renderable: Mesh): Shader = shaderStore.get(MeshShaderDefinition)
    }

    override fun create(): Renderer<Mesh> = MeshRenderer(shaderResolver, vaoStore)
}