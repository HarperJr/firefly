package com.conceptic.firefly.app.gl.view

import com.conceptic.firefly.app.game.obj.ui.UIEntity
import com.conceptic.firefly.app.gl.GLEntity
import com.conceptic.firefly.app.gl.GLEntityBufferUtils
import com.conceptic.firefly.app.gl.phisix.AABB
import com.conceptic.firefly.app.gl.shader.view.ViewShader
import com.conceptic.firefly.app.gl.support.GL
import com.conceptic.firefly.app.gl.support.vec.Vector2
import com.conceptic.firefly.app.gl.support.vec.Vector3
import com.conceptic.firefly.app.gl.texture.Texture
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL30

open class View(val aabb: AABB, private val texture: Texture = Texture.NONE) : GLEntity("view") {
    private var vertexVbo: Int = -1

    private val vertices: FloatArray
        get() = aabb.toFloatArray()

    private val hasTexture: Boolean
        get() = texture != Texture.NONE

    override fun createInternal(glEntityBufferUtils: GLEntityBufferUtils) {
        vertexVbo = glEntityBufferUtils.createVbo()
        GL.bufferData(
            vbo = vertexVbo,
            pointer = ViewShader.A_POSITION,
            size = Vector3.COMPONENTS,
            type = GL30.GL_ARRAY_BUFFER,
            arrayBuffer = vertices,
            mode = GL30.GL_STATIC_DRAW
        )

        if (hasTexture) {
            GL.bufferData(
                vbo = glEntityBufferUtils.createVbo(),
                pointer = ViewShader.A_TEX_COORD,
                size = Vector2.COMPONENTS,
                type = GL30.GL_ARRAY_BUFFER,
                arrayBuffer = floatArrayOf(), // TODO create texCoods array and fill buffer
                mode = GL30.GL_STATIC_DRAW
            )
        }
    }

    override fun renderInternal(glEntityBufferUtils: GLEntityBufferUtils) {
        GL30.glEnableVertexAttribArray(ViewShader.A_POSITION)
        if (hasTexture)
            GL30.glEnableVertexAttribArray(ViewShader.A_TEX_COORD)

//        GL.bindBufferArray(vertexVbo, GL30.GL_ARRAY_BUFFER) {
//            GL30.glBufferSubData(GL30.GL_ARRAY_BUFFER, 0, vertices)
//        }

        GL30.glDrawArrays(GL11.GL_TRIANGLE_FAN, 0, VIEW_VERTICES_COUNT)

        if (hasTexture)
            GL30.glDisableVertexAttribArray(ViewShader.A_TEX_COORD)
        GL30.glDisableVertexAttribArray(ViewShader.A_POSITION)
    }


    companion object {
        private const val VIEW_VERTICES_COUNT = 4
    }
}