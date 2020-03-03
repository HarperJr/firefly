package com.conceptic.firefly.app.game.menu

import com.conceptic.firefly.app.game.SceneEntity
import com.conceptic.firefly.app.game.camera.Camera
import com.conceptic.firefly.app.game.obj.ui.impl.Canvas
import com.conceptic.firefly.app.screen.GLFWScreen
import com.conceptic.firefly.app.screen.listener.MouseListener
import org.lwjgl.opengl.GL11

class Menu(private val screen: GLFWScreen) : SceneEntity(), MouseListener {
    override val camera: Camera = Camera(true, fov = 75f, near = -100f, far = 100f)
    private val canvas = Canvas(this)

    override fun create() {
        GL11.glClearColor(0.9f, 0.9f, 1f, 1f)
        screen.mouseListener = this
        canvas.create()
    }

    override fun destroy() {
        canvas.destroy()
        screen.mouseListener = null
    }

    override fun update() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT or GL11.GL_DEPTH_BUFFER_BIT)
        camera.update(screen.width, screen.height)
        canvas.update()
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