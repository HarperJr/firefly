package com.conceptic.firefly.app.gl.vao

import com.conceptic.firefly.app.gl.renderable.Renderable
import com.conceptic.firefly.app.gl.renderable.mesh.Mesh
import com.conceptic.firefly.app.gl.renderable.view.View
import com.conceptic.firefly.app.gl.shader.solid.MeshShader
import com.conceptic.firefly.app.gl.shader.view.ViewShader
import com.conceptic.firefly.app.gl.support.Vector2
import com.conceptic.firefly.app.gl.support.Vector3
import com.conceptic.firefly.app.gl.vbo.Vbo
import com.conceptic.firefly.app.gl.vbo.VboStore
import org.lwjgl.opengl.GL11.GL_FLOAT
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30

interface DataBinder {
    fun bind(renderable: Renderable)

    companion object : DataBinder by DataBinderImpl(VaoStore.get(), VboStore.get())
}

class DataBinderImpl(
    private val vaoStore: VaoStore,
    private val vboStore: VboStore
) : DataBinder {
    private val meshVaoBinder = MeshDataBinder(vboStore)
    private val viewVaoBinder = ViewDataBinder(vboStore)

    override fun bind(renderable: Renderable) {
        val renderableKey = renderable.name
        if (vaoStore.contains(renderableKey))
            return
        val compatibleBinder = provideBinder(renderable)

        val vao = vaoStore.newInstance(renderableKey)
        GL30.glBindVertexArray(vao)
        compatibleBinder.bind(renderable)
        GL30.glBindVertexArray(NO_VERTEX_ARRAY)
    }

    private fun provideBinder(renderable: Renderable): DataBinder {
        return when (renderable) {
            is Mesh -> meshVaoBinder
            is View -> viewVaoBinder
            else -> throw IllegalArgumentException("Unsupported renderable type ${renderable::class.simpleName}")
        }
    }

    companion object {
        private const val NO_VERTEX_ARRAY = 0
    }
}

class ViewDataBinder(
    private val vboStore: VboStore
) : DataBinder {
    override fun bind(renderable: Renderable) {
        val view = renderable as View
        val viewKey = renderable.name

        val verticesVbo = vboStore.newInstance(Vbo.vertexVbo(viewKey))
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, verticesVbo)
        GL30.glBufferData(GL30.GL_ARRAY_BUFFER, view.vertices, GL30.GL_STATIC_DRAW)
        GL30.glVertexAttribPointer(
            ViewShader.A_POSITION,
            Vector3.COMPONENTS,
            GL_FLOAT,
            false,
            Vector3.COMPONENTS * 4,
            0L
        )

        if (view.hasTexture) {
            val texCoordinatesVbo = vboStore.newInstance(Vbo.texCoordinatesVbo(viewKey))
            GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, texCoordinatesVbo)
            GL30.glBufferData(GL30.GL_ARRAY_BUFFER, view.texCoordinates, GL30.GL_STATIC_DRAW)
            GL30.glVertexAttribPointer(
                ViewShader.A_TEX_COORD,
                Vector2.COMPONENTS,
                GL_FLOAT,
                false,
                Vector2.COMPONENTS * 4,
                0L
            )
        }
    }
}

class MeshDataBinder(
    private val vboStore: VboStore
) : DataBinder {
    override fun bind(renderable: Renderable) {
        val mesh = renderable as Mesh
        val meshKey = mesh.name

        val verticesVbo = vboStore.newInstance(Vbo.vertexVbo(meshKey))
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, verticesVbo)
        GL30.glBufferData(GL30.GL_ARRAY_BUFFER, mesh.vertices, GL30.GL_STATIC_DRAW)
        GL30.glVertexAttribPointer(MeshShader.A_POSITION, Vector3.COMPONENTS, GL_FLOAT, false, 0, 0L)

        val texCoordinatesVbo = vboStore.newInstance(Vbo.texCoordinatesVbo(meshKey))
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, texCoordinatesVbo)
        GL30.glBufferData(GL30.GL_ARRAY_BUFFER, mesh.texCoordinates, GL30.GL_STATIC_DRAW)
        GL30.glVertexAttribPointer(MeshShader.A_TEX_COORD, Vector3.COMPONENTS, GL_FLOAT, false, 0, 0L)

        val normalsVbo = vboStore.newInstance(Vbo.normalsVbo(meshKey))
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, normalsVbo)
        GL30.glBufferData(GL30.GL_ARRAY_BUFFER, mesh.normals, GL30.GL_STATIC_DRAW)
        GL30.glVertexAttribPointer(MeshShader.A_NORMALS, Vector3.COMPONENTS, GL_FLOAT, false, 0, 0L)

        val elementsVbo = vboStore.newInstance(Vbo.elementsVbo(meshKey))
        GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, elementsVbo)
        GL30.glBufferData(GL30.GL_ELEMENT_ARRAY_BUFFER, mesh.elements, GL30.GL_STATIC_DRAW)
    }
}