package com.conceptic.firefly.app.gl.renderer

import com.conceptic.firefly.app.gl.mesh.Mesh
import com.conceptic.firefly.app.gl.mesh.MeshStore
import com.conceptic.firefly.app.gl.shader.ShaderStore
import com.conceptic.firefly.utils.MatrixUtils
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL13
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30

class MeshRenderer(
    private val shaderStore: ShaderStore,
    private val meshStore: MeshStore
) : Renderer<Mesh> {
    override fun render(renderable: Mesh) {
        val shader = shaderStore.resolveMeshShader(renderable)
        val meshData = meshStore.getMeshObject(renderable)

        GL30.glBindVertexArray(vao)

        GL20.glUniformMatrix4fv(
            shader.getUniform("p_matrix"),
            false,
            MatrixUtils.projectionMatrixAsBuffer
        )
        GL20.glUniformMatrix4fv(
            shader.getUniform("mv_matrix"),
            false,
            MatrixUtils.modelViewMatrixAsBuffer
        )

        GL20.glUniformMatrix4fv(shader.getUniform("u_material"), false, materialBuffer)

        GL13.glActiveTexture(GL13.GL_TEXTURE0)
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, material.getMapAmbient())
        GL20.glUniform1i(shader.getUniform("u_textures.ambient"), 0)

        GL13.glActiveTexture(GL13.GL_TEXTURE1)
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, material.getMapDiffuse())
        GL20.glUniform1i(shader.getUniform("u_textures.diffuse"), 1)

        GL13.glActiveTexture(GL13.GL_TEXTURE2)
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, material.getMapSpecular())
        GL20.glUniform1i(shader.getUniform("u_textures.specular"), 2)

        GL20.glEnableVertexAttribArray(ShaderSurface.POSITION)
        GL20.glEnableVertexAttribArray(ShaderSurface.TEXCOORD)
        GL20.glEnableVertexAttribArray(ShaderSurface.NORMAL)

        GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getIndicesCount(), GL11.GL_UNSIGNED_INT, 0)

        GL20.glDisableVertexAttribArray(ShaderSurface.POSITION)
        GL20.glDisableVertexAttribArray(ShaderSurface.TEXCOORD)
        GL20.glDisableVertexAttribArray(ShaderSurface.NORMAL)

        GL30.glBindVertexArray(0)
    }

    override fun flush() {

    }
}