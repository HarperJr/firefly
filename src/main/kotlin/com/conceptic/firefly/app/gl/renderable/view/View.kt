package com.conceptic.firefly.app.gl.renderable.view

import com.conceptic.firefly.app.gl.phisix.AABB
import com.conceptic.firefly.app.gl.renderable.Renderable
import com.conceptic.firefly.app.gl.support.Vector2
import java.nio.FloatBuffer
import java.nio.IntBuffer

abstract class View(
    override val name: String, left: Float, top: Float, right: Float, bottom: Float
) : Renderable() {
    var onClickListener: OnClickListener? = null

    override val vertices: FloatBuffer =
        FloatBuffer.wrap(floatArrayOf(left, top, 0f, right, top, 0f, right, bottom, 0f, left, bottom, 0f))
    override val texCoordinates: FloatBuffer =
        FloatBuffer.wrap(floatArrayOf(0f, 1f, 1f, 1f, 1f, 0f, 0f, 0f))
    override val elements: IntBuffer =
        IntBuffer.wrap(intArrayOf(0, 1, 2, 0, 2, 1))

    val aabb = AABB(left, top, right, bottom)

    fun instersect(x: Float, y: Float) = aabb.intersect(x, y)

    interface OnClickListener {
        fun onClick(view: View)
    }
}