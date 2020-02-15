package com.conceptic.firefly.app.gl.shader.resolver

import com.conceptic.firefly.app.gl.renderable.Renderable
import com.conceptic.firefly.app.gl.shader.Shader

interface ShaderResolver<T : Renderable> {
    fun resolve(renderable: T): Shader
}
