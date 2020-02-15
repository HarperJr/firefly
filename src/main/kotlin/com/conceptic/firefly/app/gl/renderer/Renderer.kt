package com.conceptic.firefly.app.gl.renderer

import com.conceptic.firefly.app.gl.renderable.Renderable

interface Renderer<R : Renderable> {
    fun render(renderable: R)

    fun flush()
}