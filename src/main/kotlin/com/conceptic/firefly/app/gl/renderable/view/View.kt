package com.conceptic.firefly.app.gl.renderable.view

import com.conceptic.firefly.app.gl.renderable.Renderable
import com.conceptic.firefly.app.gl.support.Bounds
import com.conceptic.firefly.app.gl.support.Vector2
import com.conceptic.firefly.app.gl.support.Vector4
import com.conceptic.firefly.app.gl.texture.Texture
import org.lwjgl.BufferUtils
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
        get() = 12

    override val vertices: FloatBuffer
        get() = BufferUtils.createFloatBuffer(verticesCount).apply {
            put(
                floatArrayOf(
                    bounds.left, bounds.top, 0f,
                    bounds.right, bounds.top, 0f,
                    bounds.right, bounds.bottom, 0f,
                    bounds.left, bounds.bottom, 0f
                )
            )
            flip()
        }

    val texCoordinates: FloatBuffer
        get() = BufferUtils.createFloatBuffer(8).apply {
            put(
                floatArrayOf(
                    0f, 1f,
                    1f, 1f,
                    1f, 0f,
                    0f, 0f
                )
            )
            flip()
        }

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