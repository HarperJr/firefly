package com.conceptic.firefly.app.gl.shader

import com.conceptic.firefly.app.gl.shader.definition.ShaderDefinition
import com.conceptic.firefly.app.gl.store.Store
import org.lwjgl.opengl.GL20

class ShaderStore : Store<ShaderDefinition, Shader>() {
    override fun create(key: ShaderDefinition): Shader = key.initShader(GL20.glCreateProgram())

    override fun clear(key: ShaderDefinition, element: Shader): Boolean {
        element.delete()
        return true
    }
}