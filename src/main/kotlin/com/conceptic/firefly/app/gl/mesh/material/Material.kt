package com.conceptic.firefly.app.gl.mesh.material

import com.conceptic.firefly.app.gl.support.Vector3

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
    val texAmbient: String,
    val texDiffuse: String,
    val texSpecular: String
)