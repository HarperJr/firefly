package com.conceptic.firefly.app.gl.phisix

import com.conceptic.firefly.app.gl.support.vec.Vector2

class AABB(l: Float, t: Float, r: Float, b: Float) {
    var left: Float = l
        private set
    var top: Float = t
        private set
    var right: Float = r
        private set
    var bottom: Float = b
        private set
    var depth: Float = 0f
        private set

    private val array = FloatArray(12)

    fun set(l: Float, t: Float, r: Float, b: Float) {
        left = l
        top = t
        right = r
        bottom = b
    }

    fun translate(vec: Vector2): AABB = this.apply {
        left += vec.x
        top += vec.y
        right += vec.x
        bottom += vec.y
    }

    fun intersect(left: Float, top: Float, right: Float, bottom: Float): Boolean {
        return this.left <= right && this.right >= left &&
                this.bottom <= top && this.top >= bottom
    }

    fun intersect(x: Float, y: Float) = intersect(x, y, x, y)

    fun intersect(aabb: AABB): Boolean = intersect(aabb.left, aabb.top, aabb.right, aabb.bottom)

    fun toFloatArray(): FloatArray {
        array[0] = left
        array[1] = top
        array[2] = depth
        array[3] = right
        array[4] = top
        array[5] = depth
        array[6] = right
        array[7] = bottom
        array[8] = depth
        array[9] = left
        array[10] = bottom
        array[11] = depth

        return array
    }
}
