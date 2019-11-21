package com.conceptic.firefly.app.gl.texture

import com.conceptic.firefly.app.gl.store.Store
import org.lwjgl.opengl.GL11

class TextureStore : Store<String, Texture>() {
    override fun clear(key: String, element: Texture): Boolean {
        GL11.glDeleteTextures(element.glPointer)
        return true
    }

    override fun create(key: String): Texture = Texture(key, GL11.glGenTextures())
}