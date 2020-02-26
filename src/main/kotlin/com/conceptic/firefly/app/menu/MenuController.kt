package com.conceptic.firefly.app.menu

import com.conceptic.firefly.app.camera.Camera
import com.conceptic.firefly.app.gl.renderable.mesh.Mesh
import com.conceptic.firefly.app.gl.renderable.mesh.loader.MeshLoader
import com.conceptic.firefly.app.gl.renderable.view.View
import com.conceptic.firefly.app.gl.renderable.view.button.Button
import com.conceptic.firefly.app.gl.renderer.mesh.MeshRenderer
import com.conceptic.firefly.app.gl.renderer.view.ViewRenderer
import com.conceptic.firefly.app.gl.shader.Shader
import com.conceptic.firefly.app.gl.shader.ShaderStore
import com.conceptic.firefly.app.gl.shader.definition.MeshShaderDefinition
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
        .zArguments(-0.1f, 256f)
        .isPerspective(false)
        .build()
    private val camera = Camera(cameraSettings)

    private val viewRenderer = ViewRenderer(ViewShaderResolver)
    private val buttons = mutableListOf<Button>()

    private val meshLoader = MeshLoader(FileProvider.get())
    private val meshRenderer = MeshRenderer(MeshShaderResolver)
    private val meshList = mutableListOf<Mesh>()

    private var screenWidth = 0
    private var screenHeight = 0

    fun init() {
        inflateButtons()

        val meshes = meshLoader.load("elementalist/Elementalist.obj")
        for (mesh in meshes)
            DataBinder.bind(mesh)
        meshList.addAll(meshes)
    }

    fun changeResolution(width: Int, height: Int) {
        screenWidth = width
        screenHeight = height
    }

    fun update() {
        camera.update(screenWidth, screenHeight)

        MatrixStackHolder.pushMatrix()
        for (button in buttons)
            viewRenderer.render(button)

        for (mesh in meshList)
            meshRenderer.render(mesh)

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
            Bounds(0f, 100f, 100f, 0f),
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

    object MeshShaderResolver : ShaderResolver<Mesh> {
        private val shaderLoader = ShaderLoader(FileProvider.get(), ShaderStore.get())
        private val meshShader: Shader by lazy { shaderLoader.load(MeshShaderDefinition) }

        override fun resolve(renderable: Mesh): Shader {
            return meshShader
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
