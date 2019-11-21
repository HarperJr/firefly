package com.conceptic.firefly.app.gl.texture

import com.conceptic.firefly.app.gl.store.Store
import org.lwjgl.opengl.GL11

class TextureStore: Store<String, Texture>() {
    override fun clear(index: String, element: Texture) = GL11.glDeleteTextures(element.glPointer)

    override fun create(index: String): Texture = Texture(index, GL11.glGenTextures())
}