package com.conceptic.firefly.app.gl.mesh

import com.conceptic.firefly.app.gl.GLEntity
import com.conceptic.firefly.app.gl.mesh.material.Material
import com.conceptic.firefly.app.gl.shader.mesh.MeshShader
import com.conceptic.firefly.app.gl.support.vec.Vector2
import com.conceptic.firefly.app.gl.support.vec.Vector3
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL30

/**
 * This class represents any mesh
 */
class Mesh constructor(name: String, private val meshData: MeshData, val material: Material) : GLEntity(name) {
    override fun loadInternal() {
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, createVbo())
        GL30.glBufferData(GL30.GL_ARRAY_BUFFER, meshData.vertices, GL30.GL_STATIC_DRAW)
        GL30.glVertexAttribPointer(MeshShader.A_POSITION, Vector3.COMPONENTS, GL11.GL_FLOAT, false, Vector3.COMPONENTS * 4, 0L)

        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, createVbo())
        GL30.glBufferData(GL30.GL_ARRAY_BUFFER, meshData.texCoords, GL30.GL_STATIC_DRAW)
        GL30.glVertexAttribPointer(MeshShader.A_TEX_COORD, Vector3.COMPONENTS, GL11.GL_FLOAT, false, Vector2.COMPONENTS * 4, 0L)

        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, createVbo())
        GL30.glBufferData(GL30.GL_ARRAY_BUFFER, meshData.texCoords, GL30.GL_STATIC_DRAW)
        GL30.glVertexAttribPointer(MeshShader.A_NORMALS, Vector3.COMPONENTS, GL11.GL_FLOAT, false, Vector3.COMPONENTS * 4, 0L)

        GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, createVbo())
        GL30.glBufferData(GL30.GL_ELEMENT_ARRAY_BUFFER, meshData.elements, GL30.GL_STATIC_DRAW)
    }
}