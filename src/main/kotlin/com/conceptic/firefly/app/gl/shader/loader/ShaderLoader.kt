package com.conceptic.firefly.app.gl.shader.loader

import com.conceptic.firefly.app.gl.shader.Shader
import com.conceptic.firefly.app.gl.shader.ShaderStore
import com.conceptic.firefly.app.gl.shader.definition.ShaderDefinition
import com.conceptic.firefly.utils.FileProvider
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL20


class ShaderContentProvider private constructor(private val fileProvider: FileProvider) {
    fun provide(shaderFileName: String): ByteArray = fileProvider.provideFileContent(shaderFileName)

    companion object {
        fun fromFileProvider(fileProvider: FileProvider) = ShaderContentProvider(fileProvider)
    }
}

class ShaderLoader(private val shaderStore: ShaderStore) {
    fun load(shaderContentProvider: ShaderContentProvider, shaderDefinition: ShaderDefinition): Shader {
        val shader = shaderStore.get(shaderDefinition.name)
        return kotlin.runCatching {
            val shaderScripts = shaderDefinition.scripts.map { shaderScript ->
                val shaderScriptContent = shaderContentProvider.provide(shaderScript.name)
                createShader(shaderScript.glShaderType, String(shaderScriptContent))
                    .also { GL20.glAttachShader(shader, it) }
            }

            shaderScripts.forEach { GL20.glDeleteShader(it) }
            return@runCatching Shader(shaderDefinition.name, shader)
        }.onSuccess { linkProgram(shader) }
            .getOrThrow()
    }

    private fun linkProgram(shaderProgram: Int) {
        GL20.glLinkProgram(shaderProgram)

        if (GL20.glGetProgrami(shaderProgram, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
            val info = GL20.glGetProgramInfoLog(shaderProgram, Int.MAX_VALUE)
            GL20.glDeleteProgram(shaderProgram)
            throw RuntimeException("Unable to link program $info")
        }
    }

    private fun createShader(type: Int, content: String): Int {
        val shader = GL20.glCreateShader(type)
        GL20.glShaderSource(shader, content)
        GL20.glCompileShader(shader)

        if (GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            val info = GL20.glGetShaderInfoLog(shader, Int.MAX_VALUE)
            GL20.glDeleteShader(shader)
            throw RuntimeException("Unable to compile shader $info")
        }

        return shader
    }

    companion object {
        private const val UNIFORM_NAME_BUFFER_CAP = 512
    }
}