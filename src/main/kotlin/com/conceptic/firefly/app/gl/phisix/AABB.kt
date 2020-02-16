package com.conceptic.firefly.app.gl.phisix

import com.conceptic.firefly.app.gl.support.Vector2

class AABB(l: Float, t: Float, r: Float, b: Float) {
    var left: Float = l
        private set
    var top: Float = t
        private set
    var right: Float = r
        private set
    var bottom: Float = b
        private set

    fun move(vec: Vector2): AABB = this.apply {
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
}
