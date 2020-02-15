package com.conceptic.firefly.app.gl.shader.solid

import com.conceptic.firefly.app.gl.renderable.mesh.material.MeshMaterial
import com.conceptic.firefly.app.gl.shader.MaterialShader
import com.conceptic.firefly.app.gl.shader.Shader

class MeshShader(shaderProgram: Int) : Shader(shaderProgram), MaterialShader<MeshMaterial> {
    override val uniforms: List<String> = listOf(
        U_PROJECTION_MATRIX,
        U_MODEL_VIEW_MATRIX,
        U_DISSOLVE_FACTOR,
        U_SPECULAR_FACTOR,
        U_AMBIENT,
        U_DIFFUSE,
        U_SPECULAR,
        U_EMISSIVE,
        U_TEX_AMBIENT,
        U_TEX_DIFFUSE,
        U_TEX_SPECULAR
    )

    override fun useMaterial(material: MeshMaterial) {
        uniformFloat(U_DISSOLVE_FACTOR, material.dissolveFactor)
        uniformFloat(U_SPECULAR_FACTOR, material.specularFactor)

        uniformVec3(U_AMBIENT, material.ambient)
        uniformVec3(U_DIFFUSE, material.diffuse)
        uniformVec3(U_SPECULAR, material.specular)
        uniformVec3(U_EMISSIVE, material.emissive)

        uniformInt(U_TEX_AMBIENT, material.texAmbient.glPointer)
        uniformInt(U_TEX_AMBIENT, material.texDiffuse.glPointer)
        uniformInt(U_TEX_AMBIENT, material.texSpecular.glPointer)
    }

    companion object {
        private const val U_DISSOLVE_FACTOR = "material.dissolveFactor"
        private const val U_SPECULAR_FACTOR = "material.specularFactor"
        private const val U_AMBIENT = "material.ambient"
        private const val U_DIFFUSE = "material.diffuse"
        private const val U_SPECULAR = "material.specular"
        private const val U_EMISSIVE = "material.emissive"
        private const val U_TEX_AMBIENT = "material.texAmbient"
        private const val U_TEX_DIFFUSE = "material.texDiffuse"
        private const val U_TEX_SPECULAR = "material.texSpecular"
    }
}