package com.conceptic.firefly.app.gl.view

import com.conceptic.firefly.app.gl.GLEntity
import com.conceptic.firefly.app.gl.phisix.AABB
import com.conceptic.firefly.app.gl.shader.view.ViewShader
import com.conceptic.firefly.app.gl.support.vec.Vector2
import com.conceptic.firefly.app.gl.support.vec.Vector3
import com.conceptic.firefly.app.gl.support.vec.Vector4
import com.conceptic.firefly.app.gl.texture.Texture
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL30

open class View(
    name: String, val aabb: AABB, val color: Vector4,
    private val texture: Texture = Texture.NONE
) : GLEntity(name) {
    private val vertices = floatArrayOf(
        aabb.left, aabb.top, 0f,
        aabb.right, aabb.top, 0f,
        aabb.right, aabb.bottom, 0f,
        aabb.left, aabb.bottom, 0f
    )

    val verticesCount: Int
        get() = 4 // Four vertices: leftTop, rightTop, rightBottom, leftBottom

    override fun loadInternal() {
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, createVbo())
        GL30.glBufferData(GL30.GL_ARRAY_BUFFER, vertices, GL30.GL_STATIC_DRAW)
        GL30.glVertexAttribPointer(
            ViewShader.A_POSITION,
            Vector3.COMPONENTS,
            GL11.GL_FLOAT,
            false,
            Vector3.COMPONENTS * 4,
            0L
        )

        if (texture != Texture.NONE) {
            GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, createVbo())
            GL30.glBufferData(GL30.GL_ARRAY_BUFFER, floatArrayOf(), GL30.GL_STATIC_DRAW)
            GL30.glVertexAttribPointer(
                ViewShader.A_TEX_COORD,
                Vector2.COMPONENTS,
                GL11.GL_FLOAT,
                false,
                Vector2.COMPONENTS * 4,
                0L
            )
        }
    }
}