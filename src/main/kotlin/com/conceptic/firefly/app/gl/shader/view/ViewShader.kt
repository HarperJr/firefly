package com.conceptic.firefly.app.gl.shader.view

import com.conceptic.firefly.app.gl.shader.Shader

class ViewShader(shaderProgram: Int) : Shader(shaderProgram) {
    override val uniforms: List<String> = listOf(U_COLOR, U_TEXTURE)

    override fun bindAttributes() {
        attributeLocation(A_POSITION, "position")
        attributeLocation(A_TEX_COORD, "texCoord")
    }

    companion object {
        // Attributes
        const val A_POSITION = 0
        const val A_TEX_COORD = 1
        // Uniforms
        const val U_COLOR = "color"
        const val U_TEXTURE = "tex"
    }
}
