package com.conceptic.firefly.app.gl.renderer.factory

import com.conceptic.firefly.app.gl.renderer.Renderable
import com.conceptic.firefly.app.gl.renderer.Renderer

interface RendererFactory<T : Renderable> {
    fun create(): Renderer<T>
}