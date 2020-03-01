package com.conceptic.firefly.app.gl.renderer.impl

import com.conceptic.firefly.app.game.camera.Camera
import com.conceptic.firefly.app.game.camera.CameraHolder
import com.conceptic.firefly.app.gl.renderer.Renderer
import com.conceptic.firefly.app.gl.shader.Shader
import com.conceptic.firefly.app.gl.shader.definition.ViewShaderDefinition
import com.conceptic.firefly.app.gl.shader.loader.ShaderLoader
import com.conceptic.firefly.app.gl.shader.view.ViewShader
import com.conceptic.firefly.app.gl.view.Button
import com.conceptic.firefly.utils.FileProvider
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL30

class ButtonRenderer : Renderer<Button> {
    private val camera: Camera
        get() = CameraHolder.get().activeCamera
    private val shader: Shader by lazy {
        ShaderLoader(FileProvider.get()).load(ViewShaderDefinition)
    }

    override fun render(entity: Button) {
        entity.bind {
            shader.use {
                uniformMat4(Shader.U_MODEL_MATRIX, entity.model)
                uniformMat4(Shader.U_VIEW_MATRIX, camera.view)
                uniformMat4(Shader.U_PROJECTION_MATRIX, camera.projection)

                uniformVec4(ViewShader.U_COLOR, entity.color)

                GL30.glEnableVertexAttribArray(ViewShader.A_POSITION)
                GL30.glDrawArrays(GL11.GL_TRIANGLE_FAN, 0, entity.verticesCount)
                GL30.glDisableVertexAttribArray(ViewShader.A_POSITION)
            }
        }
    }
}
