package com.conceptic.firefly.app.gl.vbo

import com.conceptic.firefly.app.gl.store.Store
import org.lwjgl.opengl.GL20

class VboStore : Store<VboDefinition, Vbo>() {
    override fun create(index: VboDefinition): Vbo = Vbo(GL20.glGenBuffers(), index.type)

    override fun clear(index: VboDefinition, element: Vbo): Boolean {
        GL20.glDeleteBuffers(element.glPointer)
        return true
    }
}