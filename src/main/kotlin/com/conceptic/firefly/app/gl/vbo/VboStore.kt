package com.conceptic.firefly.app.gl.vbo

import com.conceptic.firefly.app.gl.store.Store
import org.lwjgl.opengl.GL20

class VboStore : Store<VboDefinition, Vbo>() {
    override fun create(key: VboDefinition): Vbo = Vbo(GL20.glGenBuffers(), key.type)

    override fun clear(key: VboDefinition, element: Vbo): Boolean {
        GL20.glDeleteBuffers(element.glPointer)
        return true
    }
}