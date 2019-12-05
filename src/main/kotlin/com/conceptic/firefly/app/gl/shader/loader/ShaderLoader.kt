package com.conceptic.firefly.app.gl.shader.loader

import com.conceptic.firefly.app.gl.shader.Shader
import com.conceptic.firefly.app.gl.shader.ShaderStore
import com.conceptic.firefly.app.gl.shader.definition.ShaderDefinition
import com.conceptic.firefly.utils.FileProvider
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL20


class ShaderContentProvider private constructor(private val fileProvider: FileProvider) {
    fun provide(shaderFileName: String): ByteArray = fileProvider.provideFileContent(SHADERS_DIR + shaderFileName)

    companion object {
        private const val SHADERS_DIR = "shaders/"

        fun fromFileProvider(fileProvider: FileProvider) = ShaderContentProvider(fileProvider)
    }
}

class ShaderLoader(
    private val contentProvider: ShaderContentProvider,
    private val shaderStore: ShaderStore
) {
    fun load(shaderDefinition: ShaderDefinition): Shader {
        if (shaderStore.contains(shaderDefinition))
            return shaderStore.get(shaderDefinition)
        val shader = shaderStore.get(shaderDefinition)
        return kotlin.runCatching {
            val shaderScripts = shaderDefinition.scripts.map { shaderScript ->
                val shaderScriptContent = contentProvider.provide(shaderScript.name)
                createShader(shaderScript.glShaderType, String(shaderScriptContent))
                    .also { shader.attach(it) }
            }

            shaderScripts.forEach { GL20.glDeleteShader(it) }
            return@runCatching shader
        }.onSuccess { shader.link() }
            .getOrThrow()
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
}