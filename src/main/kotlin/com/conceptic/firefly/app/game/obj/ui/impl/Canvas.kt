package com.conceptic.firefly.app.game.obj.ui.impl

import com.conceptic.firefly.app.game.SceneEntity
import com.conceptic.firefly.app.game.camera.Camera
import com.conceptic.firefly.app.game.obj.ui.UIEntity
import com.conceptic.firefly.app.gl.shader.Shader
import com.conceptic.firefly.app.gl.shader.definition.ViewShaderDefinition
import com.conceptic.firefly.app.gl.shader.loader.ShaderLoader
import com.conceptic.firefly.app.screen.listener.MouseListener
import com.conceptic.firefly.utils.FileProvider

class Canvas(private val sceneEntity: SceneEntity) : MouseListener {
    private val camera: Camera = Camera(false, near = -1.5f, far = 100f)
    private var uiEntities: List<UIEntity> = emptyList()

    private val shader: Shader by lazy {
        ShaderLoader(FileProvider.get()).load(ViewShaderDefinition)
    }

    fun create() {
        val uiEntities = inflateEntities()
        uiEntities.forEach { it.create() }
        this.uiEntities = uiEntities
    }

    fun update() {
        camera.update(sceneEntity.camera.width, sceneEntity.camera.height)
        uiEntities.forEach { uiEntity ->
            shader.use {
                uniformMat4(Shader.U_VIEW_MATRIX, camera.view)
                uniformMat4(Shader.U_PROJECTION_MATRIX, camera.projection)

                uiEntity.render(this, camera.width, camera.height)
            }
        }
    }

    fun destroy() {
        uiEntities.forEach {
            it.destroy()
        }
    }

    override fun onClicked(x: Float, y: Float) {
        uiEntities.forEach { it.onClicked(x, y) }
    }

    override fun onPressed(x: Float, y: Float) {
        uiEntities.forEach { it.onPressed(x, y) }
    }

    override fun onMoved(x: Float, y: Float) {
        uiEntities.forEach { it.onMoved(x, y) }
    }

    private fun inflateEntities(): List<UIEntity> {
        return listOf(
            Button(
                "startGameBtn",
                "Start The Game",
                100,
                100,
                0xff4f4fff
            )
        )
    }
}