package com.conceptic.firefly.app.gl.shader.view

import com.conceptic.firefly.app.gl.shader.Shader
import com.conceptic.firefly.app.gl.shader.ShaderFactory
import com.conceptic.firefly.app.gl.shader.definition.MeshShaderDefinition
import com.conceptic.firefly.app.gl.shader.loader.ShaderLoader

class ViewShaderFactory(
    private val shaderLoader: ShaderLoader
) : ShaderFactory {
    override fun create(): Shader {
        return shaderLoader.load(MeshShaderDefinition)
    }
}