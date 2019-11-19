package com.conceptic.firefly.app.gl.shader.loader

import com.conceptic.firefly.app.gl.shader.Shader
import com.conceptic.firefly.app.gl.shader.ShaderStore
import com.conceptic.firefly.app.gl.shader.definition.ShaderDefinition
import com.conceptic.firefly.utils.FileProvider
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL20


class ShaderContentProvider private constructor(private val fileProvider: FileProvider) {
    fun provide(shaderFileName: String): ByteArray = fileProvider.provideFileContent(shaderFileName)

    companion object {
        fun fromFileProvider(fileProvider: FileProvider) = ShaderContentProvider(fileProvider)
    }
}

class ShaderLoader(private val shaderStore: ShaderStore) {
    private val uniformSizeBuffer = IntArray(size = 1)
    private val uniformTypeBuffer = IntArray(size = 1)
    private val activeUniformsCountBuffer = IntArray(size = 1)
    private val uniformNameSizeBuffer = IntArray(size = 1) { UNIFORM_NAME_BUFFER_CAP }
    private val uniformNameBuffer = BufferUtils.createByteBuffer(UNIFORM_NAME_BUFFER_CAP)

    fun load(shaderContentProvider: ShaderContentProvider, shaderDefinition: ShaderDefinition): Shader {
        val shaderProgram = GL20.glCreateProgram()

        val shaderScripts = shaderDefinition.scripts.map { shaderScript ->
            val shaderScriptContent = shaderContentProvider.provide(shaderScript.name)
            createShader(shaderScript.glShaderType, String(shaderScriptContent))
                .also { GL20.glAttachShader(shaderProgram, it) }
        }

        val uniforms = resolveUniformLocations(shaderProgram)

        linkProgram(shaderProgram)

        shaderScripts.forEach { GL20.glDeleteShader(it) }
        return Shader(shaderDefinition.name, shaderProgram, uniforms)
            .also { shaderStore.put(it) }
    }

    private fun linkProgram(shaderProgram: Int) {
        GL20.glLinkProgram(shaderProgram)

        if (GL20.glGetProgrami(shaderProgram, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
            val info = GL20.glGetProgramInfoLog(shaderProgram, Int.MAX_VALUE)
            GL20.glDeleteProgram(shaderProgram)
            throw RuntimeException("Unable to link program $info")
        }
    }

    private fun resolveUniformLocations(shaderProgram: Int): Map<String, Int> {
        GL20.glGetProgramiv(shaderProgram, GL20.GL_ACTIVE_UNIFORMS, activeUniformsCountBuffer)
        return (0 until activeUniformsCountBuffer[0]).associate { index ->
            GL20.glGetActiveUniform(
                shaderProgram, index, uniformNameSizeBuffer,
                uniformSizeBuffer, uniformTypeBuffer, uniformNameBuffer
            )

            val nameBytes = ByteArray(size = UNIFORM_NAME_BUFFER_CAP)
            uniformNameBuffer.get(nameBytes)

            val size = uniformSizeBuffer[0]
            val name = String(nameBytes, 0, size)
            val location = GL20.glGetUniformLocation(shaderProgram, name)

            name to location
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