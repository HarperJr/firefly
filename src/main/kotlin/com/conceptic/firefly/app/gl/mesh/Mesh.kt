package com.conceptic.firefly.app.gl.mesh

import com.conceptic.firefly.app.gl.mesh.material.MeshMaterial
import com.conceptic.firefly.app.gl.renderer.Renderable
import com.conceptic.firefly.app.gl.support.Vector3
import com.conceptic.firefly.utils.Sha1
import java.nio.IntBuffer

/**
 * This class represents any 3d mesh
 */
class Mesh private constructor(
    val name: String,
    val vertices: List<Vector3>,
    val texCoordinates: List<Vector3>,
    val normals: List<Vector3>,
    val elements: IntArray,
    val material: MeshMaterial
) : Renderable {
    val uniqueIndex = Sha1.encode(name)

    override val isOptimized: Boolean
        get() = elements.isNotEmpty()
    val elementsBuffer: IntBuffer = IntBuffer.wrap(elements)

    class Builder(private val name: String) {
        private val vertices = mutableListOf<Vector3>()
        private val texCoordinates = mutableListOf<Vector3>()
        private val normals = mutableListOf<Vector3>()
        private val elements = mutableListOf<Int>()
        private var material = MeshMaterial.EMPTY

        fun setVertices(vertices: List<Vector3>) = this.apply { this.vertices.addAll(vertices) }

        fun setTexCoordinates(texCoordinates: List<Vector3>) = this.apply { this.texCoordinates.addAll(texCoordinates) }

        fun setNormals(normals: List<Vector3>) = this.apply { this.normals.addAll(normals) }

        fun setElements(elements: List<Int>) = this.apply { this.elements.addAll(elements) }

        fun build() = Mesh(name, vertices, texCoordinates, normals, elements.toIntArray(), material)

        fun setMaterial(material: MeshMaterial) = this.apply { this.material = material }
    }

    override fun equals(other: Any?): Boolean {
        return other?.let {
            if (other is Mesh) other.name == name else false
        } ?: super.equals(other)
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}