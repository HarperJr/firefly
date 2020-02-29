package com.conceptic.firefly.app.game.menu

import com.conceptic.firefly.app.game.SceneEntity
import com.conceptic.firefly.app.game.camera.Camera
import com.conceptic.firefly.app.gl.renderer.ButtonRenderer
import com.conceptic.firefly.app.gl.phisix.AABB
import com.conceptic.firefly.app.gl.shader.Shader
import com.conceptic.firefly.app.gl.shader.definition.ViewShaderDefinition
import com.conceptic.firefly.app.gl.shader.loader.ShaderLoader
import com.conceptic.firefly.app.gl.support.vec.Vector4
import com.conceptic.firefly.app.gl.view.Button
import com.conceptic.firefly.app.screen.GLFWScreen
import com.conceptic.firefly.app.screen.listener.MouseListener
import com.conceptic.firefly.utils.FileProvider

class Menu(private val screen: GLFWScreen) : SceneEntity, MouseListener {
    private val camera = Camera(isPerspective = false)
    private val buttonRenderer = ButtonRenderer()
    private val viewShader: Shader by lazy {
        ShaderLoader(FileProvider.get()).load(ViewShaderDefinition)
    }

    private val buttons = mutableListOf<Button>()

    override fun create() {
        inflateButtons()
    }

    override fun destroy() {
        for (button in buttons)
            button.destroy()
    }

    override fun render() {
        for (button in buttons)
            viewShader.use {
                uniformMat4(Shader.U_VIEW_MATRIX, camera.view)
                uniformMat4(Shader.U_PROJECTION_MATRIX, camera.projection)
                buttonRenderer.render(button, this)
            }
    }

    private fun inflateButtons() {
        val startButton = Button(
            name = "startButton",
            aabb = AABB(0f, screen.height.toFloat(), 0f, screen.height.toFloat()),
            color = Vector4(0.5f, 0.4f, 0.2f, 1f),
            text = "Start the game"
        )
        buttons.add(startButton)
    }

    override fun onClicked(x: Float, y: Float) {
        for (button in buttons)
            button.onClick(x, y)
    }

    override fun onPressed(x: Float, y: Float) {

    }

    override fun onMoved(x: Float, y: Float) {
        for (button in buttons)
            button.onHover(x, y)
    }
}