package com.conceptic.firefly.app.gl.support.vec

import org.joml.Vector3f

class Vector3(xCoord: Float, yCoord: Float, zCoord: Float) {
    val x get() = components.x
    val y get() = components.y
    val z get() = components.z
    val normalized get() = components.normalize()

    private var components = Vector3f(xCoord, yCoord, zCoord)

    fun translate(vector: Vector3) {
        components.set(x + vector.x, y + vector.y, z + vector.z)
    }

    fun set(vector: Vector3) {
        components.set(vector.x, vector.y, vector.z)
    }

    fun distance(vector: Vector3) = components.distance(vector.x, vector.y, vector.z)

    fun rotateX(angle: Float) = components.rotateX(angle)

    fun rotateY(angle: Float) = components.rotateY(angle)

    fun rotateZ(angle: Float) = components.rotateZ(angle)

    fun cross(vector: Vector3) = components.cross(vector.x, vector.y, vector.z)

    override fun equals(other: Any?): Boolean {
        return other?.let {
            if (other is Vector3) {
                components == other.components
            } else false
        } ?: false
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        result = 31 * result + z.hashCode()
        result = 31 * result + components.hashCode()
        return result
    }

    companion object {
        const val COMPONENTS = 3
        val ZERO = Vector3(0f, 0f, 0f)
        val IDENTITY = Vector3(1f, 1f, 1f)
    }
}