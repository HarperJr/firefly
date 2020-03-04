package com.conceptic.firefly.app.game.obj

import com.conceptic.firefly.app.game.GameEntity
import com.conceptic.firefly.app.game.SceneEntity
import com.conceptic.firefly.app.game.camera.Camera
import com.conceptic.firefly.app.gl.mesh.Mesh
import com.conceptic.firefly.app.gl.shader.Shader
import com.conceptic.firefly.app.gl.shader.definition.MeshShaderDefinition
import com.conceptic.firefly.app.gl.shader.loader.ShaderLoader
import com.conceptic.firefly.utils.FileProvider

class PlayingEntity(private val sceneEntity: SceneEntity, private val meshes: List<Mesh>) : GameEntity {
    private val camera: Camera
        get() = sceneEntity.camera
    private val meshShader: Shader by lazy {
        ShaderLoader(FileProvider.get()).load(MeshShaderDefinition)
    }

    override fun create() {
        meshes.forEach {
            it.create()
        }
    }

    override fun update() {
        meshShader.use {
            uniformMat4(Shader.U_VIEW_MATRIX, camera.view)
            uniformMat4(Shader.U_PROJECTION_MATRIX, camera.projection)
            meshes.forEach { mesh ->
                uniformMat4(Shader.U_MODEL_MATRIX, mesh.modelTransform)
                mesh.render()
            }
        }
    }

    override fun destroy() {
        meshes.forEach { it.destroy() }
    }
}