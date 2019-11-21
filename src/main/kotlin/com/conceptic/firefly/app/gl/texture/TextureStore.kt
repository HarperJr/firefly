package com.conceptic.firefly.app.gl.texture

import com.conceptic.firefly.app.gl.store.Store
import org.lwjgl.opengl.GL11

class TextureStore : Store<String, Texture>() {
    override fun clear(index: String, element: Texture): Boolean {
        GL11.glDeleteTextures(element.glPointer)
        return true
    }

    override fun create(index: String): Texture = Texture(index, GL11.glGenTextures())
}