package com.conceptic.firefly.app.gl.support

import org.joml.Vector3d

class Vector3(xCoord: Double, yCoord: Double, zCoord: Double) {
    val x get() = components.x
    val y get() = components.y
    val z get() = components.z
    val normalized get() = components.normalize()

    private var components = Vector3d(xCoord, yCoord, zCoord)

    fun distance(vector: Vector3) = components.distance(vector.x, vector.y, vector.z)

    fun rotateX(angle: Double) = components.rotateX(angle)

    fun rotateY(angle: Double) = components.rotateY(angle)

    fun rotateZ(angle: Double) = components.rotateZ(angle)

    fun cross(vector: Vector3) = components.cross(vector.x, vector.y, vector.z)

    companion object {
        val ZERO = Vector3(0.0, 0.0, 0.0)
    }

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
}