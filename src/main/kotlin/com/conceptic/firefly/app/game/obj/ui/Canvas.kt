package com.conceptic.firefly.app.game.obj.ui

import com.conceptic.firefly.app.game.camera.Camera
import com.conceptic.firefly.app.gl.shader.Shader
import com.conceptic.firefly.app.gl.shader.definition.ViewShaderDefinition
import com.conceptic.firefly.app.gl.shader.loader.ShaderLoader
import com.conceptic.firefly.app.screen.listener.MouseListener
import com.conceptic.firefly.utils.FileProvider

abstract class Canvas : MouseListener {
    private val camera: Camera = Camera(false, near = -1.5f, far = 100f)
    private var uiEntities: List<UIEntity> = emptyList()

    private val shader: Shader by lazy {
        ShaderLoader(FileProvider.get()).load(ViewShaderDefinition)
    }

    protected abstract fun inflateEntities(): List<UIEntity>

    fun create() {
        val uiEntities = inflateEntities()
        uiEntities.forEach { it.create() }
        this.uiEntities = uiEntities
    }

    fun update(width: Float, height: Float) {
        camera.update(width, height)
        uiEntities.forEach { uiEntity ->
            shader.use {
                uniformMat4(Shader.U_VIEW_MATRIX, camera.view)
                uniformMat4(Shader.U_PROJECTION_MATRIX, camera.projection)

                uiEntity.render(this, width, height)
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
}