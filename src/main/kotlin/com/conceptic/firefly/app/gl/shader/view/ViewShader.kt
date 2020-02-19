package com.conceptic.firefly.app.gl.shader.view

import com.conceptic.firefly.app.gl.shader.Shader

class ViewShader(shaderProgram: Int) : Shader(shaderProgram) {
    override val uniforms: List<String> = listOf(U_DISSOLVE_FACTOR, U_AMBIENT, U_TEX_AMBIENT)

    companion object {
        private const val U_DISSOLVE_FACTOR = "material.dissolveFactor"
        private const val U_AMBIENT = "material.ambient"
        private const val U_TEX_AMBIENT = "material.texAmbient"
    }
}
