package com.conceptic.firefly.app.gl.shader

import com.conceptic.firefly.app.gl.shader.definition.ShaderDefinition
import com.conceptic.firefly.app.gl.store.Store
import org.lwjgl.opengl.GL20

class ShaderStore private constructor() : Store<ShaderDefinition, Shader>() {
    override fun create(key: ShaderDefinition): Shader = key.inflateShader(GL20.glCreateProgram())

    override fun clear(key: ShaderDefinition, element: Shader): Boolean {
        element.delete()
        return true
    }

    companion object {
        private var instance: ShaderStore? = null

        fun get(): ShaderStore {
            return synchronized(this) {
                if (instance == null)
                    instance = ShaderStore()
                instance!!
            }
        }
    }
}