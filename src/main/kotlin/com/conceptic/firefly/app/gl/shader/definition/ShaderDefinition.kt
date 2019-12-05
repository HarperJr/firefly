package com.conceptic.firefly.app.gl.shader.definition

import com.conceptic.firefly.app.gl.shader.Shader
import com.conceptic.firefly.app.gl.shader.solid.MeshShader
import com.conceptic.firefly.app.gl.shader.view.ViewShader
import kotlin.reflect.full.primaryConstructor

sealed class ShaderDefinition(val name: String) {
    abstract val scripts: List<ShaderScript>

    abstract fun initShader(shaderProgram: Int): Shader

    protected inline fun <reified T : ShaderScript> script() = T::class.primaryConstructor?.call(name)
        ?: throw IllegalStateException("Unable to create shader of type ${T::class.simpleName}")
}

object MeshShaderDefinition : ShaderDefinition("Mesh") {
    override val scripts: List<ShaderScript>
        get() = listOf(script<VertexShader>(), script<FragmentShader>())

    override fun initShader(shaderProgram: Int): Shader {
        return MeshShader(shaderProgram).init()
    }
}

object ViewShaderDefinition : ShaderDefinition("View") {
    override val scripts: List<ShaderScript>
        get() = listOf(script<VertexShader>(), script<FragmentShader>())

    override fun initShader(shaderProgram: Int): Shader {
        return ViewShader(shaderProgram).init()
    }
}