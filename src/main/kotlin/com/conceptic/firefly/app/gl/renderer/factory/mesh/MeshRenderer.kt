package com.conceptic.firefly.app.gl.renderer.factory.mesh

import com.conceptic.firefly.app.gl.mesh.Mesh
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
            if (renderable.isOptimized) {
                glBindVertexArray(vaoStore.get(renderable.uniqueIndex))
                GL11.glDrawElements(GL11.GL_TRIANGLE_STRIP, renderable.elementsBuffer)
                glBindVertexArray(0)
            } else {
                //todo another brunch of rendering process
            }
        }
    }

    override fun flush() {

    }
}