package com.conceptic.firefly.app.game.menu

import com.conceptic.firefly.app.game.SceneEntity
import com.conceptic.firefly.app.game.camera.Camera
import com.conceptic.firefly.app.game.obj.ui.Canvas
import com.conceptic.firefly.app.screen.GLFWScreen
import com.conceptic.firefly.app.screen.listener.MouseListener
import org.lwjgl.opengl.GL11

class Menu(private val screen: GLFWScreen) : SceneEntity(), MouseListener {
    override val camera: Camera = Camera(true, fov = 75f, near = -100f, far = 100f)
    private val canvas = MenuCanvas()

    private val width: Float
        get() = screen.width.toFloat()
    private val height: Float
        get() = screen.height.toFloat()

    override fun create() {
        GL11.glClearColor(0.9f, 0.9f, 1f, 1f)
        canvas.create()
    }

    override fun destroy() {
        canvas.destroy()
    }

    override fun update() {
        camera.update(width, height)
        canvas.update(width, height)
    }

    override fun onClicked(x: Float, y: Float) {
        canvas.onClicked(x, y)
    }

    override fun onPressed(x: Float, y: Float) {
        canvas.onPressed(x, y)
    }

    override fun onMoved(x: Float, y: Float) {
        canvas.onMoved(x, y)
    }
}