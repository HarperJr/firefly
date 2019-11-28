package com.conceptic.firefly.app.gl.shader.definition

import com.conceptic.firefly.app.gl.shader.Shader
import com.conceptic.firefly.app.gl.shader.solid.SolidShader
import kotlin.reflect.full.primaryConstructor

sealed class ShaderDefinition(val name: String) {
    abstract val scripts: List<ShaderScript>

    abstract fun initShader(shaderProgram: Int): Shader

    protected inline fun <reified T : ShaderScript> script() = T::class.primaryConstructor?.call(name)
        ?: throw IllegalStateException("Unable to create shader of type ${T::class.simpleName}")
}

object SolidShaderDefinition : ShaderDefinition("Solid") {
    override val scripts: List<ShaderScript>
        get() = listOf(script<VertexShader>(), script<FragmentShader>())

    override fun initShader(shaderProgram: Int): Shader {
        return SolidShader(shaderProgram).init()
    }
}