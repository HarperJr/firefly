package com.conceptic.firefly.app.menu

import com.conceptic.firefly.app.camera.Camera
import com.conceptic.firefly.app.gl.renderable.view.View
import com.conceptic.firefly.app.gl.renderable.view.button.Button
import com.conceptic.firefly.app.gl.renderer.view.ViewRenderer
import com.conceptic.firefly.app.gl.shader.Shader
import com.conceptic.firefly.app.gl.shader.ShaderStore
import com.conceptic.firefly.app.gl.shader.definition.ViewShaderDefinition
import com.conceptic.firefly.app.gl.shader.loader.ShaderLoader
import com.conceptic.firefly.app.gl.shader.resolver.ShaderResolver
import com.conceptic.firefly.app.gl.support.Bounds
import com.conceptic.firefly.app.gl.support.Vector4
import com.conceptic.firefly.app.gl.texture.Texture
import com.conceptic.firefly.app.gl.vao.DataBinder
import com.conceptic.firefly.log.Logger
import com.conceptic.firefly.utils.FileProvider
import com.conceptic.firefly.utils.MatrixStackHolder
import org.lwjgl.opengl.GL11

class MenuController {
    private val logger = Logger.getLogger<MenuController>()

    private val cameraSettings = Camera.CameraSettings.Builder
        .zArguments(0.1f, 256f)
        .isPerspective(false)
        .build()
    private val camera = Camera(cameraSettings)

    private val viewRenderer = ViewRenderer(ViewShaderResolver)
    private val buttons = mutableListOf<Button>()

    private var screenWidth = 0
    private var screenHeight = 0

    fun onShow(width: Int, height: Int) {
        screenWidth = width
        screenHeight = height

        GL11.glViewport(0, 0, width, height)

        inflateButtons()
    }

    fun onSizeChanged(width: Int, height: Int) {
        screenWidth = width
        screenHeight = height
    }

    fun onUpdate() {
        camera.update(screenWidth, screenHeight)

        MatrixStackHolder.pushMatrix()
        for (button in buttons)
            viewRenderer.render(button)
        MatrixStackHolder.popMatrix()
    }

    fun onClicked(x: Int, y: Int) {
        for (button in buttons)
            button.onClicked(x.toFloat(), y.toFloat())
    }

    fun onMoved(x: Int, y: Int) {
        for (button in buttons)
            button.onHovered(x.toFloat(), y.toFloat())
    }

    private fun inflateButtons() {
        val playTheGameButton = Button(
            "PLAY THE GAME",
            Bounds(0f, 0f, screenWidth.toFloat(), screenHeight.toFloat()),
            Vector4(0.5f, 0.3f, 0.6f, 1.0f),
            Texture.NONE
        )
            .apply { onClickListener = onPlayBtnClickListener }
        buttons.add(playTheGameButton)

        for (button in buttons)
            DataBinder.bind(button)
    }

    private val onPlayBtnClickListener = object : View.OnClickListener {
        override fun onClick(view: View) {
            logger.debug("On button clicked")
        }
    }

    object ViewShaderResolver : ShaderResolver<View> {
        private val shaderLoader = ShaderLoader(FileProvider.get(), ShaderStore.get())
        private val viewShader: Shader by lazy { shaderLoader.load(ViewShaderDefinition) }

        override fun resolve(renderable: View): Shader {
            return viewShader
        }
    }
}
