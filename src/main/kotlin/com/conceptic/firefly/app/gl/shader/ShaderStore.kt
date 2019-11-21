package com.conceptic.firefly.app.gl.shader

import com.conceptic.firefly.app.gl.store.Store
import org.lwjgl.opengl.GL20

class ShaderStore : Store<String, Int>() {
    override fun create(key: String): Int = GL20.glCreateProgram()

    override fun clear(key: String, element: Int): Boolean {
        GL20.glDeleteProgram(element)
        return true
    }
}