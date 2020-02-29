package com.conceptic.firefly.app.gl.mesh.material

import com.conceptic.firefly.app.gl.support.vec.Vector4
import com.conceptic.firefly.app.gl.texture.Texture

/**
 * This class represents materials instances
 */
class Material(
    val name: String,
    val ambient: Vector4,
    val diffuse: Vector4,
    val specular: Vector4,
    val emissive: Vector4,
    val texAmbient: Texture,
    val texDiffuse: Texture,
    val texSpecular: Texture
)