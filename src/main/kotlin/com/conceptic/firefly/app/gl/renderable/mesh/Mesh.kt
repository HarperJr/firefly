package com.conceptic.firefly.app.gl.renderable.mesh

import com.conceptic.firefly.app.gl.renderable.Renderable
import com.conceptic.firefly.app.gl.renderable.mesh.material.MeshMaterial
import com.conceptic.firefly.app.gl.support.Vector3
import com.conceptic.firefly.app.gl.texture.Texture
import org.lwjgl.BufferUtils
import java.nio.FloatBuffer
import java.nio.IntBuffer

/**
 * This class represents any 3d mesh
 */
class Mesh private constructor(
    override val name: String,
    override val verticesCount: Int,
    override val vertices: FloatBuffer,
    val texCoordinates: FloatBuffer,
    val elements: IntBuffer,
    val normals: FloatBuffer,
    val material: MeshMaterial
) : Renderable() {
    val hasTexture: Boolean
        get() = material.texAmbient != Texture.NONE

    class Builder(private val name: String) {
        private var verticesCount = 0
        private var vertices = BufferUtils.createFloatBuffer(0)
        private var texCoordinates = BufferUtils.createFloatBuffer(0)
        private var elements = BufferUtils.createIntBuffer(0)
        private var normals = BufferUtils.createFloatBuffer(0)
        private var material = MeshMaterial.EMPTY

        fun setVertices(vertices: FloatBuffer) = this.apply {
            this.verticesCount = vertices.capacity() / Vector3.COMPONENTS
            this.vertices = vertices
        }

        fun setTexCoordinates(texCoordinates: FloatBuffer) = this.apply { this.texCoordinates = texCoordinates }

        fun setNormals(normals: FloatBuffer) = this.apply { this.normals = normals }

        fun setElements(elements: IntBuffer) = this.apply { this.elements = elements }

        fun build() = Mesh(
            name,
            verticesCount,
            vertices,
            texCoordinates,
            elements,
            normals,
            material
        )

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