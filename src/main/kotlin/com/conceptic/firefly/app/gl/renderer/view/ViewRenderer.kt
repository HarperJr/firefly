package com.conceptic.firefly.app.gl.renderer.view

import com.conceptic.firefly.app.gl.renderable.view.View
import com.conceptic.firefly.app.gl.renderer.Renderer
import com.conceptic.firefly.app.gl.renderer.Renderer.Companion.NO_VERTEX_ARRAY
import com.conceptic.firefly.app.gl.shader.resolver.ShaderResolver
import com.conceptic.firefly.app.gl.vao.VaoBinder
import com.conceptic.firefly.app.gl.vao.VaoStore
import com.conceptic.firefly.app.gl.vbo.VboStore
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30

class ViewRenderer(
    private val shaderResolver: ShaderResolver<View>
) : Renderer<View> {
    private val vaoBinder: VaoBinder = VaoBinder(VaoStore.get(), VboStore.get())

    override fun render(renderable: View) {
        val shader = shaderResolver.resolve(renderable)
        shader.use {
            val viewVao = vaoBinder.getOrBind(renderable)
            GL30.glBindVertexArray(viewVao)
            GL20.glEnableVertexAttribArray(POSITION_VERTEX_ARRAY)

            GL20.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, renderable.verticesCount)

            GL20.glDisableVertexAttribArray(POSITION_VERTEX_ARRAY)
            GL30.glBindVertexArray(NO_VERTEX_ARRAY)
        }
    }

    override fun flush() {

    }

    companion object {
        private const val POSITION_VERTEX_ARRAY = 0
    }
}