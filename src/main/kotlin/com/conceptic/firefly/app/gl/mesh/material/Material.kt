package com.conceptic.firefly.app.gl.mesh.material

import com.conceptic.firefly.app.gl.support.Vector3
import com.conceptic.firefly.app.gl.texture.Texture

/**
 * This class represents materials instances
 */
data class Material(
    val name: String,
    val dissolveFactor: Float,
    val specularFactor: Float,
    val ambient: Vector3,
    val diffuse: Vector3,
    val specular: Vector3,
    val emissive: Vector3,
    val texAmbient: Texture,
    val texDiffuse: Texture,
    val texSpecular: Texture
) {
    companion object {
        val EMPTY = Material("Empty", 0f, 0f, Vector3.ZERO, Vector3.ZERO, Vector3.ZERO, Vector3.ZERO, Texture.NONE, Texture.NONE, Texture.NONE)
    }
}