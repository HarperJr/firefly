package com.conceptic.firefly.app.gl.mesh

/**
 * This class stores all mesh gl data for rendering purposes
 */
class MeshStore {
    private val meshesStore = mutableMapOf<String, Mesh>()

    fun getMesh(index: String): Mesh = meshesStore[index]
        ?: throw IllegalArgumentException("There is no data for mesh: $index in the store")
}
