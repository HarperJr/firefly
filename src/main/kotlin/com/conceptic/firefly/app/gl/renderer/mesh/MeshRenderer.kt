package com.conceptic.firefly.app.gl.renderer.mesh

import com.conceptic.firefly.app.gl.renderable.mesh.Mesh
import com.conceptic.firefly.app.gl.renderer.Renderer
import com.conceptic.firefly.app.gl.shader.resolver.ShaderResolver
import com.conceptic.firefly.app.gl.vao.VaoStore
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL30.glBindVertexArray

class MeshRenderer(
    private val shaderResolver: ShaderResolver<Mesh>,
    private val vaoStore: VaoStore
) : Renderer<Mesh> {
    override fun render(renderable: Mesh) {
        val shader = shaderResolver.resolve(renderable)

        shader.use {

        }
    }

    override fun flush() {

    }
}