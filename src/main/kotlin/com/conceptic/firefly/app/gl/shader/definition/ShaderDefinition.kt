package com.conceptic.firefly.app.gl.shader.definition

sealed class ShaderDefinition(val name: String) {
    abstract val scripts: List<ShaderScript>
}

object SolidShaderDefinition : ShaderDefinition("solid") {
    override val scripts: List<ShaderScript> = listOf(VertexShader(name), FragmentShader(name))
}