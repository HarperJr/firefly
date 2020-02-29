package com.conceptic.firefly.app.gl.renderer

import com.conceptic.firefly.app.gl.GLEntity
import com.conceptic.firefly.app.gl.shader.Shader

interface Renderer<T : GLEntity> {
    fun render(entity: T, shader: Shader)
}
