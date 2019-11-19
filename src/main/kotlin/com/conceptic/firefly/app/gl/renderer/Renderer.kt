package com.conceptic.firefly.app.gl.renderer

interface Renderer<R : Renderable> {
    fun render(renderable: R)

    fun flush()
}