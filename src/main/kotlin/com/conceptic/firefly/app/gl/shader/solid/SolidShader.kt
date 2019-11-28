package com.conceptic.firefly.app.gl.shader.solid

import com.conceptic.firefly.app.gl.shader.Shader

class SolidShader(shaderProgram: Int) : Shader(shaderProgram) {
    override val uniforms: List<String> = listOf(U_PROJECTION_MATRIX, U_MODEL_VIEW_MATRIX)

    companion object {
        const val U_PROJECTION_MATRIX = "projectionMatrix"
        const val U_MODEL_VIEW_MATRIX = "modelViewMatrix"
    }
}