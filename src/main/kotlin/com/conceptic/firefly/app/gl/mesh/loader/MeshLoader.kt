package com.conceptic.firefly.app.gl.mesh.loader

import com.conceptic.firefly.app.gl.ContentProvider
import com.conceptic.firefly.app.gl.mesh.Mesh
import com.conceptic.firefly.app.gl.mesh.MeshData
import com.conceptic.firefly.app.gl.mesh.material.Material
import com.conceptic.firefly.app.gl.support.vec.Vector4
import com.conceptic.firefly.app.gl.texture.Texture
import com.conceptic.firefly.app.gl.texture.TextureLoader
import com.conceptic.firefly.utils.FileProvider
import org.lwjgl.BufferUtils
import org.lwjgl.assimp.*
import org.lwjgl.system.MemoryUtil
import java.nio.ByteBuffer
import java.nio.IntBuffer


class MeshContentProvider(fileProvider: FileProvider) : ContentProvider(fileProvider) {
    override val dir: String
        get() = "meshes/"
}

/**
 * This is possibly would lead to a very long time routine process, should be executed at separated thread
 */
class MeshLoader(private val fileProvider: FileProvider) {
    private val textureLoader: TextureLoader = TextureLoader(fileProvider)

    fun load(meshFileName: String): List<Mesh> {
        val contentProvider = MeshContentProvider(fileProvider)
        val meshContent = contentProvider.provide(meshFileName)

        val buffer = allocateMemory(meshContent)
        val importScene = Assimp.aiImportFileFromMemory(
            buffer,
            Assimp.aiProcess_OptimizeMeshes or Assimp.aiProcess_FlipUVs or Assimp.aiProcess_JoinIdenticalVertices,
            BufferUtils.createByteBuffer(1)
        )

        return importScene?.let { scene ->
            loadMeshesFromScene(scene, loadMaterials(scene))
        } ?: throw IllegalArgumentException("Unable to create mesh $meshFileName in cause: ${Assimp.aiGetErrorString()}")
    }

    private fun allocateMemory(bytes: ByteArray): ByteBuffer {
        return MemoryUtil.memCalloc(bytes.size).apply {
            put(bytes)
            flip()
        }
    }

    private fun loadMeshesFromScene(scene: AIScene, materials: List<Material>): List<Mesh> {
        val numMeshes = scene.mNumMeshes()
        val meshesBuffer = scene.mMeshes()
        return if (meshesBuffer != null) {
            (0 until numMeshes).map { index ->
                val mesh = AIMesh.create(meshesBuffer.get(index))
                val numVertices = mesh.mNumVertices()
                val name = mesh.mName().dataString()

                val vertices: FloatArray = mesh.mVertices().let { vertex ->
                    val verticesBuffer = FloatArray(size = numVertices * AIVector3D.SIZEOF)
                    vertex.forEachIndexed { i, vec ->
                        verticesBuffer[i * VERTICES_DIMENSION_SCALE + 0] = vec.x()
                        verticesBuffer[i * VERTICES_DIMENSION_SCALE + 1] = vec.y()
                        verticesBuffer[i * VERTICES_DIMENSION_SCALE + 2] = vec.z()
                    }
                    verticesBuffer
                }

                val texCoords: FloatArray = mesh.mTextureCoords(0)?.let { texCoords ->
                    val texCoordinatesBuffer = FloatArray(size = numVertices * TEXCOORDS_DIMENSION_SCALE)
                    texCoords.forEachIndexed { i, vec ->
                        texCoordinatesBuffer[i * TEXCOORDS_DIMENSION_SCALE + 0] = vec.x()
                        texCoordinatesBuffer[i * TEXCOORDS_DIMENSION_SCALE + 1] = vec.y()
                    }
                    texCoordinatesBuffer
                } ?: floatArrayOf()

                val normals: FloatArray = mesh.mNormals()?.let { norms ->
                    val texCoordinatesBuffer = FloatArray(size = numVertices * NORMALS_DIMENSION_SCALE)
                    norms.forEachIndexed { i, vec ->
                        texCoordinatesBuffer[i * NORMALS_DIMENSION_SCALE + 0] = vec.x()
                        texCoordinatesBuffer[i * NORMALS_DIMENSION_SCALE + 1] = vec.y()
                        texCoordinatesBuffer[i * NORMALS_DIMENSION_SCALE + 2] = vec.z()
                    }
                    texCoordinatesBuffer
                } ?: floatArrayOf()

                val elements: IntArray = mesh.mFaces().let { faces ->
                    val indicesList = mutableListOf<Int>()
                    for (face in faces) {
                        val faceIndices = face.mIndices()
                        while (faceIndices.hasRemaining())
                            indicesList.add(faceIndices.get())
                    }
                    indicesList.toIntArray()
                }

                val material = materials[mesh.mMaterialIndex()]
                val meshData = MeshData(vertices, texCoords, normals, elements)
                return@map Mesh(name, meshData, material)
            }
        } else emptyList()
    }

    private fun loadMaterials(scene: AIScene): List<Material> {
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
                    ambient = ambient,
                    diffuse = diffuse,
                    emissive = emissive,
                    specular = specular,
                    texAmbient = texAmbient,
                    texDiffuse = texDiffuse,
                    texSpecular = texSpecular
                )
            }
        } else emptyList()
    }

    private fun getColor(material: AIMaterial, type: String): Vector4 = with(AIColor4D.create()) {
        val result = Assimp.aiGetMaterialColor(material, type, Assimp.aiTextureType_NONE, 0, this)
        if (result == 0) Vector4(
            this.r(),
            this.g(),
            this.b(),
            this.a()
        ) else Vector4.IDENTITY
    }

    private fun getTexture(material: AIMaterial, type: Int): Texture {
        val textureFileName = with(AIString.calloc()) {
            Assimp.aiGetMaterialTexture(material, type, 0, this, null as IntBuffer?, null, null, null, null, null)
            this.dataString()
        }

        return if (textureFileName.isNotEmpty()) {
            textureLoader.load(textureFileName)
        } else Texture.NONE
    }

    companion object {
        private const val VERTICES_DIMENSION_SCALE = 3
        private const val TEXCOORDS_DIMENSION_SCALE = 2
        private const val NORMALS_DIMENSION_SCALE = 3
    }
}