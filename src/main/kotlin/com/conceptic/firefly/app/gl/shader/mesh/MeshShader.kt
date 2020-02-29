package com.conceptic.firefly.app.gl.shader.mesh

import com.conceptic.firefly.app.gl.shader.Shader

class MeshShader(shaderProgram: Int) : Shader(shaderProgram) {
    override val uniforms: List<String> = listOf(
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

    override fun bindAttributes() {
        attributeLocation(A_POSITION, "position")
        attributeLocation(A_TEX_COORD, "texCoord")
        attributeLocation(A_NORMALS, "normal")
    }

    companion object {
        const val A_POSITION = 0
        const val A_TEX_COORD = 1
        const val A_NORMALS = 2
        // Uniforms
        const val U_DISSOLVE_FACTOR = "material.dissolveFactor"
        const val U_SPECULAR_FACTOR = "material.specularFactor"
        const val U_AMBIENT = "material.ambient"
        const val U_DIFFUSE = "material.diffuse"
        const val U_SPECULAR = "material.specular"
        const val U_EMISSIVE = "material.emissive"
        const val U_TEX_AMBIENT = "material.texAmbient"
        const val U_TEX_DIFFUSE = "material.texDiffuse"
        const val U_TEX_SPECULAR = "material.texSpecular"

    }
}