package com.conceptic.firefly.app.gl.shader

import com.conceptic.firefly.app.gl.support.Vector3
import com.conceptic.firefly.app.gl.support.Vector4
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL20
import java.nio.FloatBuffer

abstract class Shader(private val shaderProgram: Int) {
    protected abstract val uniforms: List<String>
    private val standardUniforms = listOf(U_PROJECTION_MATRIX, U_MODEL_VIEW_MATRIX)

    fun init() = this.apply {
        val uniforms = standardUniforms.union(uniforms)
        uniforms.forEach { uniform ->
            uniformLocations[uniform] = GL20.glGetUniformLocation(shaderProgram, uniform)
        }
    }

    fun use(process: Shader.() -> Unit) {
        GL20.glUseProgram(shaderProgram)
        process.invoke(this)
        GL20.glUseProgram(NO_PROGRAM)
    }

    fun attach(shaderScript: Int) {
        GL20.glAttachShader(shaderProgram, shaderScript)
    }

    fun link() {
        GL20.glValidateProgram(shaderProgram)
        GL20.glLinkProgram(shaderProgram)

        if (GL20.glGetProgrami(shaderProgram, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
            val info = GL20.glGetProgramInfoLog(shaderProgram, Int.MAX_VALUE)
            GL20.glDeleteProgram(shaderProgram)
            throw RuntimeException("Unable to link program $info")
        }
    }

    fun delete() {
        GL20.glDeleteProgram(shaderProgram)
    }

    fun uniformFloat(uniform: String, float: Float) = GL20.glUniform1f(uniformLocation(uniform), float)

    fun uniformVec3(uniform: String, vector3: Vector3) =
        GL20.glUniform3f(uniformLocation(uniform), vector3.x, vector3.y, vector3.z)

    fun uniformInt(uniform: String, int: Int) = GL20.glUniform1i(uniformLocation(uniform), int)

    fun uniformVec4(uniform: String, vector4: Vector4) =
        GL20.glUniform4f(uniformLocation(uniform), vector4.x, vector4.y, vector4.z, vector4.w)

    fun uniformMat3(uniform: String, mat3: FloatBuffer) = GL20.glUniformMatrix3fv(uniformLocation(uniform), false, mat3)

    fun uniformMat4(uniform: String, mat4: FloatBuffer) = GL20.glUniformMatrix4fv(uniformLocation(uniform), false, mat4)

    private fun uniformLocation(uniform: String) = uniformLocations[uniform]
        ?: throw IllegalArgumentException("Unable to find uniform $uniform in shader")

    private val uniformLocations = mutableMapOf<String, Int>()

    companion object {
        private const val NO_PROGRAM = 0

        const val U_PROJECTION_MATRIX = "projectionMatrix"
        const val U_MODEL_VIEW_MATRIX = "modelViewMatrix"
    }
}