package com.conceptic.firefly.app.gl.renderer.factory.view

import com.conceptic.firefly.app.gl.renderer.Renderer
import com.conceptic.firefly.app.gl.renderer.factory.RendererFactory
import com.conceptic.firefly.app.gl.shader.Shader
import com.conceptic.firefly.app.gl.shader.ShaderStore
import com.conceptic.firefly.app.gl.shader.definition.ViewShaderDefinition
import com.conceptic.firefly.app.gl.shader.resolver.ShaderResolver
import com.conceptic.firefly.app.gl.view.View

class ViewRendererFactory(
    private val shaderStore: ShaderStore
) : RendererFactory<View> {
    private val shaderResolver = object : ShaderResolver<View> {
        override fun resolve(renderable: View): Shader {
            return shaderStore.get(ViewShaderDefinition)
        }
    }

    override fun create(): Renderer<View> {
        return ViewRenderer(shaderResolver)
    }
}