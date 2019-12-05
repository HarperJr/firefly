package com.conceptic.firefly.app.gl.view.material

import com.conceptic.firefly.app.gl.renderer.Material
import com.conceptic.firefly.app.gl.support.Vector3
import com.conceptic.firefly.app.gl.texture.Texture

data class ViewMaterial(
    override val name: String,
    val dissolveFactor: Float,
    val ambient: Vector3,
    val diffuse: Vector3,
    val texAmbient: Texture,
    val texDiffuse: Texture
) : Material() {
    companion object {
        val EMPTY = ViewMaterial("Empty", 0f, Vector3.ZERO, Vector3.ZERO, Texture.NONE, Texture.NONE)
    }
}