package com.conceptic.firefly.app.gl.shader

import org.lwjgl.opengl.GL30

class ShaderStore {
    private val shaders = mutableMapOf<String, Shader>()

    fun getShader(index: String) {

    }

    fun put(shader: Shader) {
        shaders[shader.uniqueIndex] = shader
    }

    fun clear() {
        shaders.forEach { _, shader -> GL30.glDeleteProgram(shader.program) }
    }
}