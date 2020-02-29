package com.conceptic.firefly.app.gl.view

import com.conceptic.firefly.app.gl.phisix.AABB
import com.conceptic.firefly.app.gl.support.vec.Vector4
import com.conceptic.firefly.app.gl.texture.Texture

class Button(name: String, aabb: AABB, color: Vector4, texture: Texture = Texture.NONE, private val text: String) :
    View(name, aabb, color, texture) {
    var onClickListener: OnClickListener? = null
    private var isHovered = false

    fun onClick(x: Float, y: Float) {
        if (aabb.intersect(x, y))
            onClickListener?.onClick(this, x, y)
    }

    fun onHover(x: Float, y: Float) {
        isHovered = aabb.intersect(x, y)
    }

    interface OnClickListener {
        fun onClick(button: Button, x: Float, y: Float)
    }
}