package com.conceptic.firefly.app.gl.shader

data class Shader(val type: ShaderType, val name: String, val programIdentifier: Int)

enum class ShaderType {
    VERTEX, FRAGMENT, OBJECT
}