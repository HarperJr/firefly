package com.conceptic.firefly.app.gl.renderer.mesh

import com.conceptic.firefly.app.gl.renderable.mesh.Mesh
import com.conceptic.firefly.app.gl.renderer.Renderer
import com.conceptic.firefly.app.gl.shader.Shader
import com.conceptic.firefly.app.gl.shader.resolver.ShaderResolver
import com.conceptic.firefly.app.gl.shader.view.ViewShader
import com.conceptic.firefly.app.gl.vao.VaoStore
import com.conceptic.firefly.utils.MatrixStackHolder
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30

class MeshRenderer(
    private val shaderResolver: ShaderResolver<Mesh>
) : Renderer<Mesh> {
    private val vaoStore = VaoStore.get()

    override fun render(renderable: Mesh) {
        val shader = shaderResolver.resolve(renderable)
        shader.use {
            GL30.glBindVertexArray(vaoStore[renderable.name])

            uniformMat4(Shader.U_PROJECTION_MATRIX, MatrixStackHolder.projectionMatrixAsBuffer)
            uniformMat4(Shader.U_MODEL_VIEW_MATRIX, MatrixStackHolder.modelViewMatrixAsBuffer)

            GL20.glEnableVertexAttribArray(ViewShader.A_POSITION)

            if (renderable.hasTexture)
                GL20.glEnableVertexAttribArray(ViewShader.A_TEX_COORD)
            //GL11.glBindTexture(GL11.GL_TEXTURE_2D, renderable.texture.glPointer)
            GL20.glDrawElements(GL11.GL_TRIANGLE_FAN, renderable.elements)

            if (renderable.hasTexture)
                GL20.glDisableVertexAttribArray(ViewShader.A_TEX_COORD)
            GL20.glDisableVertexAttribArray(ViewShader.A_POSITION)
            GL30.glBindVertexArray(Renderer.NO_VERTEX_ARRAY)
        }
    }

    override fun flush() {

    }
}