package com.conceptic.firefly.app.gl.mesh

import com.conceptic.firefly.app.gl.GLEntity
import com.conceptic.firefly.app.gl.GLEntityBufferUtils
import com.conceptic.firefly.app.gl.mesh.material.Material
import com.conceptic.firefly.app.gl.shader.mesh.MeshShader
import com.conceptic.firefly.app.gl.support.GL
import com.conceptic.firefly.app.gl.support.vec.Vector2
import com.conceptic.firefly.app.gl.support.vec.Vector3
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL30

/**
 * This class represents any mesh
 */
class Mesh constructor(name: String, private val meshData: MeshData, val material: Material) : GLEntity(name) {
    private val elementsCount: Int
        get() = meshData.elements.count()

    override fun createInternal(glEntityBufferUtils: GLEntityBufferUtils) {
        GL.bufferData(
            vbo = glEntityBufferUtils.createVbo(),
            pointer = MeshShader.A_POSITION,
            size = Vector3.COMPONENTS,
            type = GL30.GL_ARRAY_BUFFER,
            arrayBuffer = meshData.vertices,
            mode = GL30.GL_STATIC_DRAW
        )

        GL.bufferData(
            vbo = glEntityBufferUtils.createVbo(),
            pointer = MeshShader.A_TEX_COORD,
            size = Vector2.COMPONENTS,
            type = GL30.GL_ARRAY_BUFFER,
            arrayBuffer = meshData.texCoords,
            mode = GL30.GL_STATIC_DRAW
        )

        GL.bufferData(
            vbo = glEntityBufferUtils.createVbo(),
            pointer = MeshShader.A_NORMALS,
            size = Vector3.COMPONENTS,
            type = GL30.GL_ARRAY_BUFFER,
            arrayBuffer = meshData.normals,
            mode = GL30.GL_STATIC_DRAW
        )

        GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, glEntityBufferUtils.createVbo())
        GL30.glBufferData(GL30.GL_ELEMENT_ARRAY_BUFFER, meshData.elements, GL30.GL_STATIC_DRAW)
    }

    override fun renderInternal(glEntityBufferUtils: GLEntityBufferUtils) {
        GL30.glEnableVertexAttribArray(MeshShader.A_POSITION)
        GL30.glEnableVertexAttribArray(MeshShader.A_TEX_COORD)
        GL30.glEnableVertexAttribArray(MeshShader.A_NORMALS)

        GL30.glDrawElements(GL11.GL_TRIANGLES, elementsCount, GL11.GL_UNSIGNED_INT, 0)

        GL30.glDisableVertexAttribArray(MeshShader.A_POSITION)
        GL30.glDisableVertexAttribArray(MeshShader.A_TEX_COORD)
        GL30.glDisableVertexAttribArray(MeshShader.A_NORMALS)
    }
}