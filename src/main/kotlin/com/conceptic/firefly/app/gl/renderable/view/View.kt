package com.conceptic.firefly.app.gl.renderable.view

import com.conceptic.firefly.app.gl.renderable.Renderable
import com.conceptic.firefly.app.gl.support.Bounds
import com.conceptic.firefly.app.gl.support.Vector2
import com.conceptic.firefly.app.gl.support.Vector3
import com.conceptic.firefly.app.gl.support.Vector4
import com.conceptic.firefly.app.gl.texture.Texture
import java.nio.FloatBuffer

abstract class View(
    override val name: String,
    private val bounds: Bounds,
    val color: Vector4,
    val texture: Texture
) : Renderable() {
    val hasTexture: Boolean
        get() = texture != Texture.NONE

    override val verticesCount: Int
        get() = vertices.capacity() / Vector3.COMPONENTS

    override val vertices: FloatBuffer = FloatBuffer.wrap(
        floatArrayOf(
            bounds.left, bounds.top, 0f,
            bounds.right, bounds.top, 0f,
            bounds.right, bounds.bottom, 0f,
            bounds.left, bounds.bottom, 0f
        )
    )

    val texCoordinates: FloatBuffer = FloatBuffer.wrap(
        floatArrayOf(0f, 1f, 1f, 1f, 1f, 0f, 0f, 0f)
    )

    var onClickListener: View.OnClickListener? = null
    var isDirty: Boolean = false
        private set

    fun setPosition(vec: Vector2) {
        bounds.translate(vec)
        isDirty = true
    }

    fun onClicked(x: Float, y: Float) {

    }

    fun onHovered(x: Float, y: Float) {

    }

    interface OnClickListener {
        fun onClick(view: View)
    }
}