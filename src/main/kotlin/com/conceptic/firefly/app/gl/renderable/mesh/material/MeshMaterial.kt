package com.conceptic.firefly.app.gl.renderable.mesh.material

import com.conceptic.firefly.app.gl.renderer.Material
import com.conceptic.firefly.app.gl.support.Vector3
import com.conceptic.firefly.app.gl.support.Vector4
import com.conceptic.firefly.app.gl.texture.Texture

/**
 * This class represents materials instances
 */
data class MeshMaterial(
    override val name: String,
    val ambient: Vector4,
    val diffuse: Vector4,
    val specular: Vector4,
    val emissive: Vector4,
    val texAmbient: Texture,
    val texDiffuse: Texture,
    val texSpecular: Texture
) : Material {
    companion object {
        val EMPTY = MeshMaterial(
            "Empty",
            Vector4.ZERO,
            Vector4.ZERO,
            Vector4.ZERO,
            Vector4.ZERO,
            Texture.NONE,
            Texture.NONE,
            Texture.NONE
        )
    }
}