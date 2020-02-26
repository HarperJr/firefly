package com.conceptic.firefly.app.gl.renderer.view

import com.conceptic.firefly.app.gl.renderable.view.View
import com.conceptic.firefly.app.gl.renderer.Renderer
import com.conceptic.firefly.app.gl.renderer.Renderer.Companion.NO_VERTEX_ARRAY
import com.conceptic.firefly.app.gl.shader.Shader
import com.conceptic.firefly.app.gl.shader.resolver.ShaderResolver
import com.conceptic.firefly.app.gl.shader.view.ViewShader
import com.conceptic.firefly.app.gl.vao.VaoStore
import com.conceptic.firefly.utils.MatrixStackHolder
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30

class ViewRenderer(
    private val shaderResolver: ShaderResolver<View>
) : Renderer<View> {
    private val vaoStore: VaoStore = VaoStore.get()

    override fun render(renderable: View) {
        val shader = shaderResolver.resolve(renderable)
        shader.use {
            GL30.glBindVertexArray(vaoStore[renderable.name])

            uniformMat4(Shader.U_PROJECTION_MATRIX, MatrixStackHolder.projectionMatrixAsBuffer)
            uniformMat4(Shader.U_MODEL_VIEW_MATRIX, MatrixStackHolder.modelViewMatrixAsBuffer)
            uniformVec4(ViewShader.U_COLOR, renderable.color)

            GL20.glEnableVertexAttribArray(ViewShader.A_POSITION)

            GL20.glDrawArrays(GL11.GL_TRIANGLE_FAN, 0, renderable.verticesCount)

            GL20.glDisableVertexAttribArray(ViewShader.A_POSITION)

            GL30.glBindVertexArray(NO_VERTEX_ARRAY)
        }
    }
}