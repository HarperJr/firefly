package com.conceptic.firefly.app.gl.vbo

class Vbo private constructor(private val type: VboType, private val index: String) {
    override fun equals(other: Any?): Boolean {
        return other?.let {
            if (other is Vbo) other.type == type && other.index == index else false
        } ?: super.equals(other)
    }

    override fun hashCode(): Int {
        var result = type.hashCode()
        result = 31 * result + index.hashCode()
        return result
    }

    override fun toString(): String {
        return "${type.name}_$index"
    }

    companion object {
        fun vertexVbo(index: String) = Vbo(VboType.VERTICES, index)
        fun colorVbo(index: String) = Vbo(VboType.COLOR, index)
        fun texCoordinatesVbo(index: String) = Vbo(VboType.TEX_COORDINATES, index)
        fun normalsVbo(index: String) = Vbo(VboType.NORMALS, index)
        fun elementsVbo(index: String) = Vbo(VboType.ELEMENTS, index)
    }
}