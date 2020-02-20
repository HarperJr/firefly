package com.conceptic.firefly.app.gl.renderable.mesh.loader

import com.conceptic.firefly.app.gl.renderable.mesh.Mesh
import com.conceptic.firefly.app.gl.renderable.mesh.material.MeshMaterial
import com.conceptic.firefly.app.gl.support.Vector4
import com.conceptic.firefly.app.gl.texture.Texture
import com.conceptic.firefly.app.gl.texture.TextureLoader
import com.conceptic.firefly.app.gl.texture.TextureStore
import com.conceptic.firefly.utils.FileProvider
import org.lwjgl.BufferUtils
import org.lwjgl.assimp.*
import org.lwjgl.system.MemoryUtil
import java.nio.FloatBuffer
import java.nio.IntBuffer


class MeshContentProvider private constructor(private val fileProvider: FileProvider) {
    fun provide(meshFileName: String): ByteArray =
        fileProvider.provideFileContent(MESH_DIR + meshFileName)

    companion object {
        private const val MESH_DIR = "meshes/"

        fun fromFileProvider(fileProvider: FileProvider) =
            MeshContentProvider(fileProvider)
    }
}

/**
 * This is possibly would lead to a very long time routine process, should be executed at separated thread
 */
class MeshLoader(
    private val fileProvider: FileProvider
) {
    private val textureLoader: TextureLoader = TextureLoader(fileProvider, TextureStore.get())

    fun load(meshFileName: String): List<Mesh> {
        val contentProvider = MeshContentProvider.fromFileProvider(fileProvider)
        val meshContent = contentProvider.provide(meshFileName)

        val byteBuffer = MemoryUtil.memCalloc(meshContent.size)
        byteBuffer.put(meshContent)
        byteBuffer.flip()

        val importScene = Assimp.aiImportFileFromMemory(
            byteBuffer,
            Assimp.aiProcess_OptimizeMeshes or Assimp.aiProcess_FlipUVs or Assimp.aiProcess_JoinIdenticalVertices,
            BufferUtils.createByteBuffer(1)
        )

        return importScene?.let { scene ->
            val materials = loadRawMaterials(scene)
            loadRawSubMeshes(scene, materials)
        } ?: throw IllegalArgumentException("Unable to load mesh $meshFileName in cause: ${Assimp.aiGetErrorString()}")
    }

    private fun loadRawSubMeshes(scene: AIScene, meshMaterials: List<MeshMaterial>): List<Mesh> {
        val numMeshes = scene.mNumMeshes()
        val meshesPointerBuffer = scene.mMeshes()
        return if (meshesPointerBuffer != null) {
            (0 until numMeshes).map { index ->
                val rawMesh = AIMesh.create(meshesPointerBuffer.get(index))
                val name = rawMesh.mName().dataString()

                val vertices = rawMesh.mVertices().let { vert ->
                    val verticesBuffer = FloatArray(size = vert.count() * VERTICES_DIMENSION_SCALE)
                    vert.forEachIndexed { i, vec ->
                        verticesBuffer[i * VERTICES_DIMENSION_SCALE + 0] = vec.x()
                        verticesBuffer[i * VERTICES_DIMENSION_SCALE + 1] = vec.y()
                        verticesBuffer[i * VERTICES_DIMENSION_SCALE + 2] = vec.z()
                    }
                    FloatBuffer.wrap(verticesBuffer)
                }

                val texCoordinates = rawMesh.mTextureCoords(0)?.let { texCoords ->
                    val texCoordinatesBuffer = FloatArray(size = texCoords.count() * TEXCOORDS_DIMENSION_SCALE)
                    texCoords.forEachIndexed { i, vec ->
                        texCoordinatesBuffer[i * TEXCOORDS_DIMENSION_SCALE + 0] = vec.x()
                        texCoordinatesBuffer[i * TEXCOORDS_DIMENSION_SCALE + 1] = vec.y()
                    }
                    FloatBuffer.wrap(texCoordinatesBuffer)
                }

                val normals = rawMesh.mNormals()?.let { norms ->
                    val texCoordinatesBuffer = FloatArray(size = norms.count() * NORMALS_DIMENSION_SCALE)
                    norms.forEachIndexed { i, vec ->
                        texCoordinatesBuffer[i * NORMALS_DIMENSION_SCALE + 0] = vec.x()
                        texCoordinatesBuffer[i * NORMALS_DIMENSION_SCALE + 1] = vec.y()
                        texCoordinatesBuffer[i * NORMALS_DIMENSION_SCALE + 2] = vec.z()
                    }
                    FloatBuffer.wrap(texCoordinatesBuffer)
                }

                val elements = rawMesh.mFaces().mIndices()

                val materialIndex = rawMesh.mMaterialIndex()
                val meshBuilder = Mesh.Builder(name)
                    .setVertices(vertices)
                    .setMaterial(meshMaterials[materialIndex])
                    .setElements(elements)

                if (texCoordinates != null)
                    meshBuilder.setTexCoordinates(texCoordinates)
                if (normals != null)
                    meshBuilder.setNormals(normals)

                return@map meshBuilder.build()
            }
        } else emptyList()
    }

    private fun loadRawMaterials(scene: AIScene): List<MeshMaterial> {
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

                MeshMaterial(
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
        if (result == 0) Vector4(this.r(), this.g(), this.b(), this.a()) else Vector4.IDENTITY
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