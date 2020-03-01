package com.conceptic.firefly.app.gl.renderer

import com.conceptic.firefly.app.gl.GLEntity

interface Renderer<T : GLEntity> {
    fun render(entity: T)
}
