package com.conceptic.firefly.app.gl.mesh.loader

import com.conceptic.firefly.app.gl.mesh.Mesh
import com.conceptic.firefly.app.gl.mesh.MeshStore
import com.conceptic.firefly.app.gl.mesh.SolidMesh
import com.conceptic.firefly.app.gl.mesh.material.Material
import com.conceptic.firefly.app.gl.support.Vector3
import com.conceptic.firefly.app.gl.support.Vector4
import com.conceptic.firefly.app.gl.texture.TextureLoader
import com.conceptic.firefly.utils.FileProvider
import org.lwjgl.assimp.*
import java.nio.ByteBuffer
import java.nio.IntBuffer
import java.nio.file.Paths


class MeshContentProvider private constructor(private val fileProvider: FileProvider) {
    fun provide(meshFileName: String): ByteArray =
        fileProvider.provideFileContent(Paths.get(MESH_DIR, meshFileName).toString())

    companion object {
        private const val MESH_DIR = "meshes/"

        fun fromFileProvider(fileProvider: FileProvider) = MeshContentProvider(fileProvider)
    }
}

/**
 * This is possibly would lead to a very long time routine process, should be executed at separated thread
 */
class MeshLoader(
    private val contentProvider: MeshContentProvider,
    private val meshStore: MeshStore,
    private val textureLoader: TextureLoader
) {
    fun load(meshFileName: String): List<Mesh> {
        val meshContent = contentProvider.provide(meshFileName)
        val importScene = Assimp.aiImportFile(
            ByteBuffer.wrap(meshContent),
            Assimp.aiProcess_OptimizeMeshes or Assimp.aiProcess_FlipUVs or Assimp.aiProcess_JoinIdenticalVertices
        )

        return importScene?.let { scene ->
            val materials = loadRawMaterials(scene)
            loadRawSubMeshes(scene, materials)
        } ?: throw IllegalArgumentException("Unable to load mesh: $meshFileName")
    }

    private fun loadRawSubMeshes(scene: AIScene, materials: List<Material>): List<SolidMesh> {
        val numMeshes = scene.mNumMeshes()
        val meshesPointerBuffer = scene.mMeshes()
        return if (meshesPointerBuffer != null) {
            (0 until numMeshes).map { index ->
                val rawMesh = AIMesh.create(meshesPointerBuffer.get(index))
                val name = rawMesh.mName().dataString()

                val vertices = rawMesh.mVertices().map { vertex -> Vector3(vertex.x(), vertex.y(), vertex.z()) }
                val texCoordinates = rawMesh.mTextureCoords(0)
                    ?.map { texCoord -> Vector3(texCoord.x(), texCoord.y(), texCoord.z()) } ?: emptyList()
                val normals = rawMesh.mNormals()
                    ?.map { normal -> Vector3(normal.x(), normal.y(), normal.z()) } ?: emptyList()
                val indexes = with(mutableListOf<Int>()) {
                    rawMesh.mFaces().map { face ->
                        val indexes = face.mIndices()
                        Array(size = indexes.remaining()) { indexes.get(index) }
                    }.forEach { this@with.addAll(it) }
                    this
                }
                val materialIndex = rawMesh.mMaterialIndex()

                SolidMesh(name, vertices, indexes, texCoordinates, normals, materials[materialIndex])
            }
        } else emptyList()
    }

    private fun loadRawMaterials(scene: AIScene): List<Material> {
        val numMaterials = scene.mNumMaterials()
        val materialsPointerBuffer = scene.mMaterials()
        return if (materialsPointerBuffer != null) {
            (0 until numMaterials).map { index ->
                val rawMaterial = AIMaterial.create(materialsPointerBuffer.get(index))

                val namePointer = AIString.calloc()
                Assimp.aiGetMaterialString(rawMaterial, Assimp.AI_MATKEY_NAME, Assimp.AI_AISTRING, 0, namePointer)

                val name = namePointer.dataString()
                val ambient = getColor(rawMaterial, Assimp.AI_MATKEY_COLOR_AMBIENT)
                val diffuse = getColor(rawMaterial, Assimp.AI_MATKEY_COLOR_DIFFUSE)
                val specular = getColor(rawMaterial, Assimp.AI_MATKEY_COLOR_SPECULAR)
                val emissive = getColor(rawMaterial, Assimp.AI_MATKEY_COLOR_EMISSIVE)
                val texAmbient = getTexture(rawMaterial, Assimp.aiTextureType_AMBIENT)
                val texDiffuse = getTexture(rawMaterial, Assimp.aiTextureType_DIFFUSE)
                val texSpecular = getTexture(rawMaterial, Assimp.aiTextureType_SPECULAR)

                Material(
                    name = name,
                    dissolveFactor = ambient.w,
                    specularFactor = specular.w,
                    ambient = ambient.toVector3(),
                    diffuse = diffuse.toVector3(),
                    emissive = emissive.toVector3(),
                    specular = specular.toVector3(),
                    texAmbient = textureLoader.load(texAmbient),
                    texDiffuse = textureLoader.load(texDiffuse),
                    texSpecular = textureLoader.load(texSpecular)
                )
            }
        } else emptyList()
    }

    private fun getColor(material: AIMaterial, type: String): Vector4 = with(AIColor4D.create()) {
        val result = Assimp.aiGetMaterialColor(material, type, Assimp.aiTextureType_NONE, 0, this)
        if (result == 0) Vector4(this.r(), this.g(), this.b(), this.a()) else Vector4.IDENTITY
    }

    private fun getTexture(material: AIMaterial, type: Int) = with(AIString.calloc()) {
        Assimp.aiGetMaterialTexture(material, type, 0, this, null as IntBuffer?, null, null, null, null, null)
        this.dataString()
    }
}