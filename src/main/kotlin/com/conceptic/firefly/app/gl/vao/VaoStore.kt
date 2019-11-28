package com.conceptic.firefly.app.gl.vao

import com.conceptic.firefly.app.gl.store.Store
import org.lwjgl.opengl.GL30

class VaoStore : Store<String, Int>() {
    override fun create(key: String): Int = GL30.glGenVertexArrays()

    override fun clear(key: String, element: Int): Boolean {
        GL30.glDeleteVertexArrays(element)
        return true
    }
}
