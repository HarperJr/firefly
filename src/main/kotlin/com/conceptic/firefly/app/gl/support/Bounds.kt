package com.conceptic.firefly.app.gl.support

class Bounds(l: Float, t: Float, r: Float, b: Float) {
    var left: Float = l
        private set

    var top: Float = t
        private set

    var right: Float = r
        private set

    var bottom: Float = b
        private set


    fun translate(vec: Vector2) {
        left += vec.x
        right += vec.x
        top += vec.y
        bottom += vec.y
    }
}
