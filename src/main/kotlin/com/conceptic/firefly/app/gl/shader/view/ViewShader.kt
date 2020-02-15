package com.conceptic.firefly.app.gl.shader.view

import com.conceptic.firefly.app.gl.shader.MaterialShader
import com.conceptic.firefly.app.gl.shader.Shader
import com.conceptic.firefly.app.gl.view.ViewMaterial

class ViewShader(shaderProgram: Int) : Shader(shaderProgram), MaterialShader<ViewMaterial> {
    override val uniforms: List<String> = listOf(U_DISSOLVE_FACTOR, U_AMBIENT, U_TEX_AMBIENT)

    override fun useMaterial(material: ViewMaterial) {
        uniformFloat(U_DISSOLVE_FACTOR, material.dissolveFactor)
        uniformVec3(U_AMBIENT, material.ambient)
        uniformInt(U_TEX_AMBIENT, material.texAmbient.glPointer)
    }

    companion object {
        private const val U_DISSOLVE_FACTOR = "material.dissolveFactor"
        private const val U_AMBIENT = "material.ambient"
        private const val U_TEX_AMBIENT = "material.texAmbient"
    }
}
