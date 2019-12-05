package com.conceptic.firefly.app.gl.shader.view

import com.conceptic.firefly.app.gl.view.material.ViewMaterial
import com.conceptic.firefly.app.gl.shader.MaterialShader
import com.conceptic.firefly.app.gl.shader.Shader

class ViewShader(shaderProgram: Int) : Shader(shaderProgram), MaterialShader<ViewMaterial> {
    override val uniforms: List<String>
        get() = listOf(
            U_MODEL_VIEW_MATRIX,
            U_PROJECTION_MATRIX,
            U_DISSOLVE_FACTOR,
            U_AMBIENT,
            U_DIFFUSE,
            U_TEX_AMBIENT,
            U_TEX_DIFFUSE
        )

    override fun useMaterial(material: ViewMaterial) {
        uniformFloat(U_DISSOLVE_FACTOR, material.dissolveFactor)

        uniformVec3(U_AMBIENT, material.ambient)
        uniformVec3(U_DIFFUSE, material.diffuse)

        uniformInt(U_TEX_AMBIENT, material.texAmbient.glPointer)
        uniformInt(U_TEX_DIFFUSE, material.texDiffuse.glPointer)
    }

    companion object {
        private const val U_DISSOLVE_FACTOR = "material.dissolveFactor"
        private const val U_AMBIENT = "material.ambient"
        private const val U_DIFFUSE = "material.diffuse"
        private const val U_TEX_AMBIENT = "material.texAmbient"
        private const val U_TEX_DIFFUSE = "material.texDiffuse"
    }
}
