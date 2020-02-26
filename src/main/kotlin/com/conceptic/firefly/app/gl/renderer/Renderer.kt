package com.conceptic.firefly.app.gl.renderer

import com.conceptic.firefly.app.gl.renderable.Renderable

interface Renderer<R : Renderable> {
    fun render(renderable: R)

    companion object {
        const val NO_VERTEX_ARRAY = 0
    }
}