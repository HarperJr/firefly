package com.conceptic.firefly.app.gl.vao

import com.conceptic.firefly.app.gl.renderable.Renderable
import com.conceptic.firefly.app.gl.renderable.mesh.Mesh
import com.conceptic.firefly.app.gl.renderable.view.View
import com.conceptic.firefly.app.gl.support.Vector3
import com.conceptic.firefly.app.gl.vbo.Vbo
import com.conceptic.firefly.app.gl.vbo.VboStore
import org.lwjgl.opengl.GL11.GL_FLOAT
import org.lwjgl.opengl.GL30

class VaoBinder(
    private val vaoStore: VaoStore,
    private val vboStore: VboStore
) {
    fun getOrBind(renderable: Renderable): Int {
        val renderableKey = renderable.name
        val vaoIsBinded = vaoStore.contains(renderableKey)
        return if (vaoIsBinded) {
            vaoStore[renderableKey]
        } else {
            when (renderable) {
                is Mesh -> bindMesh(renderable)
                is View -> bindView(renderable)
                else -> throw IllegalArgumentException("Unsupported renderable type ${renderable::class.simpleName}")
            }
        }
    }

    private fun bindMesh(mesh: Mesh): Int {
        val meshKey = mesh.name

        val vao = vaoStore.newInstance(meshKey)
        GL30.glBindVertexArray(vao)

        val verticesVbo = vboStore.newInstance(Vbo.vertexVbo(meshKey))
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, verticesVbo)
        GL30.glBufferData(GL30.GL_ARRAY_BUFFER, mesh.vertices, GL30.GL_STATIC_DRAW)
        GL30.glVertexAttribPointer(0, Vector3.COMPONENTS, GL_FLOAT, false, 0, 0L)

        val texCoordinatesVbo = vboStore.newInstance(Vbo.texCoordinatesVbo(meshKey))
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, texCoordinatesVbo)
        GL30.glBufferData(GL30.GL_ARRAY_BUFFER, mesh.texCoordinates, GL30.GL_STATIC_DRAW)
        GL30.glVertexAttribPointer(1, Vector3.COMPONENTS, GL_FLOAT, false, 0, 0L)

        val normalsVbo = vboStore.newInstance(Vbo.normalsVbo(meshKey))
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, normalsVbo)
        GL30.glBufferData(GL30.GL_ARRAY_BUFFER, mesh.normals, GL30.GL_STATIC_DRAW)
        GL30.glVertexAttribPointer(2, Vector3.COMPONENTS, GL_FLOAT, false, 0, 0L)

        val elementsVbo = vboStore.newInstance(Vbo.elementsVbo(meshKey))
        GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, elementsVbo)
        GL30.glBufferData(GL30.GL_ELEMENT_ARRAY_BUFFER, mesh.elements, GL30.GL_STATIC_DRAW)

        GL30.glBindVertexArray(NO_VERTEX_ARRAY)

        return vao
    }

    private fun bindView(view: View): Int {
        val viewKey = view.name
        val vao = vaoStore.newInstance(viewKey)

        GL30.glBindVertexArray(vao)

        val verticesVbo = vboStore.newInstance(Vbo.vertexVbo(viewKey))
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, verticesVbo)
        GL30.glBufferData(GL30.GL_ARRAY_BUFFER, view.vertices, GL30.GL_DYNAMIC_DRAW)
        GL30.glVertexAttribPointer(0, Vector3.COMPONENTS, GL_FLOAT, false, 0, 0)

        val texCoordinatesVbo = vboStore.newInstance(Vbo.texCoordinatesVbo(viewKey))
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, texCoordinatesVbo)
        GL30.glBufferData(GL30.GL_ARRAY_BUFFER, view.texCoordinates, GL30.GL_STATIC_DRAW)
        GL30.glVertexAttribPointer(1, Vector3.COMPONENTS, GL_FLOAT, false, 0, 0L)

        GL30.glBindVertexArray(NO_VERTEX_ARRAY)

        return vao
    }

    companion object {
        private const val NO_VERTEX_ARRAY = 0
    }
}