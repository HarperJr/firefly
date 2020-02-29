package com.conceptic.firefly.app.gl.renderer

import com.conceptic.firefly.app.gl.shader.Shader
import com.conceptic.firefly.app.gl.shader.view.ViewShader
import com.conceptic.firefly.app.gl.view.Button
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL30

class ButtonRenderer : Renderer<Button> {
    override fun render(entity: Button, shader: Shader) {
        entity.bind {
            GL30.glEnableVertexAttribArray(ViewShader.A_POSITION)
            GL30.glDrawArrays(GL11.GL_TRIANGLE_FAN, 0, entity.verticesCount)
            GL30.glDisableVertexAttribArray(ViewShader.A_POSITION)
        }
    }
}
