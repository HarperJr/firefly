package com.conceptic.firefly.app.gl.renderer.view

import com.conceptic.firefly.app.gl.renderable.view.View
import com.conceptic.firefly.app.gl.renderer.Renderer
import com.conceptic.firefly.app.gl.shader.resolver.ShaderResolver

class ViewRenderer(
    private val shaderResolver: ShaderResolver<View>
) : Renderer<View> {
    override fun render(renderable: View) {
        val shader = shaderResolver.resolve(renderable)
        shader.use {

        }
    }

    override fun flush() {

    }
}