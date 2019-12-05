package com.conceptic.firefly.app.gl.renderer.factory.view

import com.conceptic.firefly.app.gl.renderer.Renderer
import com.conceptic.firefly.app.gl.shader.MaterialShader
import com.conceptic.firefly.app.gl.shader.resolver.ShaderResolver
import com.conceptic.firefly.app.gl.view.View

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