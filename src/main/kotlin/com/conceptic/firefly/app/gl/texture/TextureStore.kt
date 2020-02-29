package com.conceptic.firefly.app.gl.texture

import org.lwjgl.opengl.GL11

class TextureStore {
    private val textures = mutableMapOf<String, Texture>()

    fun getTexture(textureName: String): Texture? = textures[textureName]

    fun createTexture(textureName: String): Texture {
        return Texture(textureName, GL11.glGenTextures())
            .also { tex -> textures[textureName] = tex }
    }

    fun deleteTexture(textureName: String): Boolean {
        return textures.remove(textureName) != null
    }

    companion object {
        private var instance: TextureStore? = null

        fun get(): TextureStore {
            return synchronized(this) {
                if (instance == null)
                    instance = TextureStore()
                instance!!
            }
        }
    }
}
