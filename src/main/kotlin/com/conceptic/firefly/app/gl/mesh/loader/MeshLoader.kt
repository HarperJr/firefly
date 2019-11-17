package com.conceptic.firefly.app.gl.mesh.loader

import com.conceptic.firefly.utils.FileProvider

class RawMeshContentProvider(private val fileProvider: FileProvider) {
    fun provideRaw(rawMeshFileName: String): ByteArray = fileProvider.provideFileContent(rawMeshFileName)
}

object MeshLoader {
    fun loadRaw(contentProvider: RawMeshContentProvider, rawPath: String): RawMesh {
        val rawContent = contentProvider.provideRaw(rawPath)
        return RawMesh()
    }
}