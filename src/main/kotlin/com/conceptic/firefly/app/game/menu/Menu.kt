package com.conceptic.firefly.app.game.menu

import com.conceptic.firefly.app.game.SceneEntity
import com.conceptic.firefly.app.game.camera.Camera
import com.conceptic.firefly.app.game.camera.CameraHolder
import com.conceptic.firefly.app.gl.mesh.Mesh
import com.conceptic.firefly.app.gl.mesh.loader.MeshLoader
import com.conceptic.firefly.app.gl.phisix.AABB
import com.conceptic.firefly.app.gl.renderer.impl.ButtonRenderer
import com.conceptic.firefly.app.gl.renderer.impl.MeshRenderer
import com.conceptic.firefly.app.gl.support.vec.Vector4
import com.conceptic.firefly.app.gl.view.Button
import com.conceptic.firefly.app.screen.GLFWScreen
import com.conceptic.firefly.app.screen.listener.MouseListener
import com.conceptic.firefly.support.loadAsync
import com.conceptic.firefly.utils.FileProvider
import org.lwjgl.opengl.GL11

class Menu(private val screen: GLFWScreen) : SceneEntity, MouseListener {
    private val camera = Camera(isPerspective = false, near = -1f, far = 100f)
    private val buttonRenderer = ButtonRenderer()
    private val meshRenderer = MeshRenderer()

    private val buttons = mutableListOf<Button>()
    private var meshes: List<Mesh> = emptyList()

    override fun create() {
        GL11.glClearColor(0.4f, 0.4f, 1f, 1f)

        CameraHolder.get().activeCamera = camera

        inflateMesh()
        inflateButtons()
    }

    override fun destroy() {
        for (button in buttons)
            button.destroy()
        for(mesh in meshes)
            mesh.destroy()
    }

    override fun update() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT or GL11.GL_DEPTH_BUFFER_BIT)

        camera.update(screen.width, screen.height)
        for (button in buttons)
            buttonRenderer.render(button)
        for(mesh in meshes)
            meshRenderer.render(mesh)
    }

    private fun inflateMesh() {
        loadAsync {
            MeshLoader(FileProvider.get()).load("elementalist/Elementalist.obj")
        }.then { meshes ->
            meshes.forEach {
                it.create()
            }
            this.meshes = meshes
        }
    }

    private fun inflateButtons() {
        val startButton = Button(
            name = "startButton",
            aabb = AABB(64f, screen.height.toFloat() - 124f, screen.width.toFloat() - 64f, screen.height.toFloat() - 164f),
            color = Vector4(0.5f, 0.4f, 0.2f, 1f),
            text = "Start the game"
        )

        startButton.create()
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