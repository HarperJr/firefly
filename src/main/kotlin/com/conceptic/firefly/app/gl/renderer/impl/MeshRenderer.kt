package com.conceptic.firefly.app.gl.renderer.impl

import com.conceptic.firefly.app.game.camera.Camera
import com.conceptic.firefly.app.game.camera.CameraHolder
import com.conceptic.firefly.app.gl.mesh.Mesh
import com.conceptic.firefly.app.gl.renderer.Renderer
import com.conceptic.firefly.app.gl.shader.Shader
import com.conceptic.firefly.app.gl.shader.definition.MeshShaderDefinition
import com.conceptic.firefly.app.gl.shader.loader.ShaderLoader
import com.conceptic.firefly.app.gl.shader.mesh.MeshShader
import com.conceptic.firefly.utils.FileProvider
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL30

class MeshRenderer : Renderer<Mesh> {
    private val camera: Camera
        get() = CameraHolder.get().activeCamera
    private val shader: Shader by lazy {
        ShaderLoader(FileProvider.get()).load(MeshShaderDefinition)
    }

    override fun render(entity: Mesh) {
        entity.bind {
            shader.use {
                entity.scale(40f, 40f, 40f)
                entity.translate(10f, 10f, 0f)

                uniformMat4(Shader.U_MODEL_MATRIX, entity.model)
                uniformMat4(Shader.U_VIEW_MATRIX, camera.view)
                uniformMat4(Shader.U_PROJECTION_MATRIX, camera.projection)

                GL30.glEnableVertexAttribArray(MeshShader.A_POSITION)
                GL30.glEnableVertexAttribArray(MeshShader.A_TEX_COORD)
                GL30.glEnableVertexAttribArray(MeshShader.A_NORMALS)

                GL30.glDrawElements(GL11.GL_TRIANGLES, entity.elementsCount, GL11.GL_UNSIGNED_INT, 0)

                GL30.glDisableVertexAttribArray(MeshShader.A_POSITION)
                GL30.glDisableVertexAttribArray(MeshShader.A_TEX_COORD)
                GL30.glDisableVertexAttribArray(MeshShader.A_NORMALS)
            }
        }
    }
}
