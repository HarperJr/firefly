package com.conceptic.firefly.app.gl.shader.solid

import com.conceptic.firefly.app.gl.shader.Shader
import com.conceptic.firefly.app.gl.shader.ShaderFactory
import com.conceptic.firefly.app.gl.shader.definition.MeshShaderDefinition
import com.conceptic.firefly.app.gl.shader.loader.ShaderLoader

class MeshShaderFactory(
    private val shaderLoader: ShaderLoader
) : ShaderFactory {
    override fun create(): Shader {
        return shaderLoader.load(MeshShaderDefinition)
    }
}