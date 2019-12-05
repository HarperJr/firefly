package com.conceptic.firefly.app.gl.shader

import com.conceptic.firefly.app.gl.renderer.Material

interface MaterialShader<M : Material> {
    fun useMaterial(material: M)
}