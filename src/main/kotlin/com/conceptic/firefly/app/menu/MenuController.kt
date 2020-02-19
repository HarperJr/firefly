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
import com.conceptic.firefly.app.gl.texture.Texture
import com.conceptic.firefly.app.gl.vao.VaoStore
import com.conceptic.firefly.app.gl.vbo.VboStore
import com.conceptic.firefly.log.Logger
import com.conceptic.firefly.utils.FileProvider

class MenuController {
    private val logger = Logger.getLogger<MenuController>()

    private val cameraSettings = Camera.CameraSettings.Builder
        .zArguments(1000f, -1000f)
        .isPerspective(false)
        .build()
    private val camera = Camera(cameraSettings)
    private val viewRenderer = ViewRenderer(ViewShaderResolver, VaoStore.get(), VboStore.get())

    private val buttons = mutableListOf<Button>()

    fun onShow(width: Int, height: Int) {
        inflateButtons(width, height)
    }

    fun onUpdate(width: Int, height: Int) {
        camera.update(width, height)
        for (button in buttons)
            viewRenderer.render(button)
    }

    fun onClicked(x: Int, y: Int) {
        for (button in buttons)
            button.onClicked(x.toFloat(), y.toFloat())
    }

    fun onMoved(x: Int, y: Int) {
        for (button in buttons)
            button.onHovered(x.toFloat(), y.toFloat())
    }

    private fun inflateButtons(width: Int, height: Int) {
        val playTheGameButton = Button("PLAY THE GAME", width / 2f, 20f, width - width / 2f, 60f, Texture.NONE)
            .apply { onClickListener = onPlayBtnClickListener }
        buttons.add(playTheGameButton)
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
