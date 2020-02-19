package com.conceptic.firefly.app.gl.renderable.view

import com.conceptic.firefly.app.gl.phisix.AABB
import com.conceptic.firefly.app.gl.renderable.Renderable
import com.conceptic.firefly.app.gl.support.Vector2
import com.conceptic.firefly.app.gl.texture.Texture
import java.nio.FloatBuffer
import java.nio.IntBuffer

abstract class View(
    override val name: String,
    left: Float, top: Float, right: Float, bottom: Float,
    private val texture: Texture
) : Renderable() {
    override val vertices: FloatBuffer =
        FloatBuffer.wrap(floatArrayOf(left, top, 0f, right, top, 0f, right, bottom, 0f, left, bottom, 0f))
    override val texCoordinates: FloatBuffer =
        FloatBuffer.wrap(floatArrayOf(0f, 1f, 1f, 1f, 1f, 0f, 0f, 0f))

    val aabb = AABB(left, top, right, bottom)

    var onClickListener: View.OnClickListener? = null
    var isDirty: Boolean = false

    fun onClicked(x: Float, y: Float) {

    }

    fun onHovered(x: Float, y: Float) {

    }

    interface OnClickListener {
        fun onClick(view: View)
    }
}