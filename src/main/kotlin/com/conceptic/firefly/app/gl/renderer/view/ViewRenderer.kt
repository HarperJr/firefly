package com.conceptic.firefly.app.gl.renderer.view

import com.conceptic.firefly.app.gl.renderable.view.View
import com.conceptic.firefly.app.gl.renderer.Renderer
import com.conceptic.firefly.app.gl.renderer.Renderer.Companion.NO_VERTEX_ARRAY
import com.conceptic.firefly.app.gl.shader.resolver.ShaderResolver
import com.conceptic.firefly.app.gl.vao.VaoStore
import com.conceptic.firefly.app.gl.vbo.Vbo
import com.conceptic.firefly.app.gl.vbo.VboStore
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30

class ViewRenderer(
    private val shaderResolver: ShaderResolver<View>,
    private val vaoStore: VaoStore,
    private val vboStore: VboStore
) : Renderer<View> {
    private val bindedVaos = mutableSetOf<String>()

    override fun render(renderable: View) {
        val viewKey = renderable.name
        val shader = shaderResolver.resolve(renderable)
        shader.use {
            if (vaoStore.contains(viewKey)) {
                if (renderable.isDirty) {
                    // Update data using vao
                }

                GL30.glBindVertexArray(vaoStore[viewKey])
                GL20.glEnableVertexAttribArray(POSITION_VERTEX_ARRAY)

                GL20.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, renderable.verticesCount)

                GL20.glDisableVertexAttribArray(POSITION_VERTEX_ARRAY)
                GL30.glBindVertexArray(NO_VERTEX_ARRAY)
            } else {
                if (!bindedVaos.add(viewKey)) return@use
                val viewVao = vaoStore.newInstance(viewKey)

                GL30.glBindVertexArray(viewVao)

                val vertexVbo = vboStore.newInstance(Vbo.vertexVbo(viewKey))
                GL30.glBindBuffer(GL30.GL_VERTEX_ARRAY, vertexVbo)
                GL30.glBufferData(GL30.GL_ARRAY_BUFFER, renderable.vertices, GL30.GL_DYNAMIC_DRAW)

                GL30.glBindVertexArray(NO_VERTEX_ARRAY)
            }
        }
    }

    override fun flush() {
        bindedVaos.forEach { vaoStore.remove(it) }
    }

    companion object {
        private const val POSITION_VERTEX_ARRAY = 0
    }
}