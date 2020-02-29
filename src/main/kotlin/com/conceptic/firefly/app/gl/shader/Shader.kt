package com.conceptic.firefly.app.gl.shader

import com.conceptic.firefly.app.gl.support.vec.Vector3
import com.conceptic.firefly.app.gl.support.vec.Vector4
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL20

abstract class Shader(private val shaderProgram: Int) {
    protected abstract val uniforms: List<String>
    private val sharedUniforms = listOf(U_MODEL_MATRIX, U_VIEW_MATRIX, U_PROJECTION_MATRIX)

    abstract fun bindAttributes()

    fun use(process: Shader.() -> Unit) {
        GL20.glUseProgram(shaderProgram)
        process.invoke(this)
        GL20.glUseProgram(NO_PROGRAM)
    }

    fun attachShaderScript(shaderScript: Int) {
        GL20.glAttachShader(shaderProgram, shaderScript)
    }

    fun linkShaderProgram() {
        GL20.glLinkProgram(shaderProgram)
        GL20.glValidateProgram(shaderProgram)

        if (GL20.glGetProgrami(shaderProgram, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
            val info = GL20.glGetProgramInfoLog(shaderProgram, Int.MAX_VALUE)
            GL20.glDeleteProgram(shaderProgram)
            throw RuntimeException("Unable to link program $info")
        }

        bindAttributes()
        bindUniforms()
    }

    fun deleteShaderProgram() {
        GL20.glDeleteProgram(shaderProgram)
    }

    fun uniformFloat(uniform: String, float: Float) = GL20.glUniform1f(uniformLocation(uniform), float)

    fun uniformVec3(uniform: String, vector3: Vector3) =
        GL20.glUniform3f(uniformLocation(uniform), vector3.x, vector3.y, vector3.z)

    fun uniformInt(uniform: String, int: Int) = GL20.glUniform1i(uniformLocation(uniform), int)

    fun uniformVec4(uniform: String, vector4: Vector4) =
        GL20.glUniform4f(uniformLocation(uniform), vector4.x, vector4.y, vector4.z, vector4.w)

    fun uniformMat3(uniform: String, mat3: FloatArray) = GL20.glUniformMatrix3fv(uniformLocation(uniform), false, mat3)

    fun uniformMat4(uniform: String, mat4: FloatArray) = GL20.glUniformMatrix4fv(uniformLocation(uniform), false, mat4)

    protected fun attributeLocation(attribute: Int, attributePointer: String) {
        GL20.glBindAttribLocation(shaderProgram, attribute, attributePointer)
    }

    private fun uniformLocation(uniform: String) = uniformLocations[uniform]
        ?: throw IllegalArgumentException("Unable to find uniform $uniform in shader")

    private val uniformLocations = mutableMapOf<String, Int>()

    private fun bindUniforms() = this.apply {
        val allUniforms = sharedUniforms.union(uniforms)
        allUniforms.forEach { uniform ->
            uniformLocations[uniform] = GL20.glGetUniformLocation(shaderProgram, uniform)
        }
    }

    companion object {
        private const val NO_PROGRAM = 0
        const val U_MODEL_MATRIX = "modelMatrix"
        const val U_VIEW_MATRIX = "viewMatrix"
        const val U_PROJECTION_MATRIX = "projectionMatrix"
    }
}