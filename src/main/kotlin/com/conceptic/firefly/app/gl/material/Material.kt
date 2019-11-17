package com.conceptic.firefly.app.gl.material

import com.conceptic.firefly.app.gl.support.Vector3

/**
 * This class represents materials instances
 */
data class Material(
    val name: String,
    val shader: String,
    val dissolveFactor: Float,
    val specularFactor: Float,
    val ambient: Vector3,
    val diffuse: Vector3,
    val specular: Vector3,
    val emissive: Vector3,
    val mapAmbient: String,
    val mapDiffuse: String,
    val mapSpecular: String
)