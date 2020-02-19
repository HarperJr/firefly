package com.conceptic.firefly.app.gl.vbo

import com.conceptic.firefly.app.gl.store.Store
import org.lwjgl.opengl.GL20

class VboStore private constructor() : Store<Vbo, Int>() {
    override fun create(key: Vbo): Int = GL20.glGenBuffers()

    override fun clear(key: Vbo, element: Int): Boolean {
        GL20.glDeleteBuffers(element)
        return true
    }

    companion object {
        private var instance: VboStore? = null

        fun get(): VboStore {
            return synchronized(this) {
                if (instance == null)
                    instance = VboStore()
                instance!!
            }
        }
    }
}