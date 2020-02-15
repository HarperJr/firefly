package com.conceptic.firefly.app.gl.vao

import com.conceptic.firefly.app.gl.renderable.mesh.Mesh
import com.conceptic.firefly.app.gl.renderable.Renderable
import com.conceptic.firefly.app.gl.support.Vector3
import com.conceptic.firefly.app.gl.vbo.Vbo
import com.conceptic.firefly.app.gl.vbo.VboStore
import com.conceptic.firefly.utils.VectorUtils
import org.lwjgl.opengl.GL11.GL_FLOAT
import org.lwjgl.opengl.GL30

class VaoBinder(
    private val vboStore: VboStore,
    private val vaoStore: VaoStore
) {
    fun bind(renderable: Renderable) = when (renderable) {
        is Mesh -> bindMesh(renderable)
        else -> throw IllegalArgumentException("Unsupported renderable type ${renderable::class.simpleName}")
    }

    private fun bindMesh(mesh: Mesh) {
        val index = mesh.uniqueIndex

        val vao = vaoStore.get(index)
        GL30.glBindVertexArray(vao)

        val verticesVbo = vboStore.get(Vbo.elementsVbo(index))
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, verticesVbo)

        GL30.glBufferData(GL30.GL_ARRAY_BUFFER, mesh.vertices, GL30.GL_STATIC_DRAW)
        GL30.glVertexAttribPointer(0, Vector3.COMPONENTS, GL_FLOAT, false, 0, 0L)

        val texCoordinatesVbo = vboStore.get(Vbo.elementsVbo(index))
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, texCoordinatesVbo)

        GL30.glBufferData(GL30.GL_ARRAY_BUFFER, mesh.texCoordinates, GL30.GL_STATIC_DRAW)
        GL30.glVertexAttribPointer(1, Vector3.COMPONENTS, GL_FLOAT, false, 0, 0L)

        val normalsVbo = vboStore.get(Vbo.elementsVbo(index))
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, normalsVbo)

        GL30.glBufferData(GL30.GL_ARRAY_BUFFER, mesh.normals, GL30.GL_STATIC_DRAW)
        GL30.glVertexAttribPointer(2, Vector3.COMPONENTS, GL_FLOAT, false, 0, 0L)

        if (mesh.isOptimized) {
            val elementBuffer = vboStore.get(Vbo.elementsVbo(index))
            GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, elementBuffer)
            GL30.glBufferData(GL30.GL_ELEMENT_ARRAY_BUFFER, mesh.elements, GL30.GL_STATIC_DRAW)
        }

        GL30.glBindVertexArray(NO_VERTEX_ARRAY)
    }

    companion object {
        private const val NO_VERTEX_ARRAY = 0
    }
}