package com.conceptic.firefly.app.gl.support.vec

import org.joml.Vector4f

class Vector4(xCoord: Float, yCoord: Float, zCoord: Float, wCoord: Float) {
    val x get() = components.x
    val y get() = components.y
    val z get() = components.z
    val w get() = components.w
    val normalized get() = components.normalize()

    private var components = Vector4f(xCoord, yCoord, zCoord, wCoord)

    fun distance(vector: Vector4) = components.distance(vector.x, vector.y, vector.z, vector.w)

    fun rotateX(angle: Float) = components.rotateX(angle)

    fun rotateY(angle: Float) = components.rotateY(angle)

    fun rotateZ(angle: Float) = components.rotateZ(angle)

    fun toVector3() = Vector3(x, y, z)

    operator fun minus(value: Float): Vector4 {
        return Vector4(x - value, y - value, z - value, w - value)
    }

    override fun equals(other: Any?): Boolean {
        return other?.let {
            if (other is Vector4) {
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
        const val COMPONENTS = 4
        val ZERO = Vector4(0f, 0f, 0f, 0f)
        val IDENTITY = Vector4(1f, 1f, 1f, 1f)

        fun fromBytes(bytes: Long): Vector4 {
            return Vector4(
                (bytes shr 0x18 and 0xff) / 255f,
                (bytes shr 0x12 and 0xff) / 255f,
                (bytes shr 0x06 and 0xff) / 255f,
                (bytes shr 0x00 and 0xff) / 255f
            )
        }
    }
}