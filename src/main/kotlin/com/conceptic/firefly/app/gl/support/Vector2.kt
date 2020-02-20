package com.conceptic.firefly.app.gl.support

import org.joml.Vector2f

class Vector2(xCoord: Float, yCoord: Float) {
    val x get() = components.x
    val y get() = components.y
    val normalized get() = components.normalize()

    private var components = Vector2f(xCoord, yCoord)

    fun distance(vector: Vector2) = components.distance(vector.x, vector.y)

    fun toVector2() = Vector2(x, y)

    override fun equals(other: Any?): Boolean {
        return other?.let {
            if (other is Vector2) {
                components == other.components
            } else false
        } ?: false
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        result = 31 * result + components.hashCode()
        return result
    }

    companion object {
        const val COMPONENTS = 2
        val ZERO = Vector2(0f, 0f)
        val IDENTITY = Vector2(1f, 1f)
    }
}