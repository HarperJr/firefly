package com.conceptic.firefly.app.gl

import com.conceptic.firefly.app.scene.SceneDispatcher
import com.conceptic.firefly.app.scene.controller.SceneController
import com.conceptic.firefly.log.Logger
import com.conceptic.firefly.screen.support.ScreenUpdatesPublisher
import com.conceptic.firefly.screen.support.ScreenUpdatesSubscriber
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*

class GLSurfaceController(
    private val screenUpdatesPublisher: ScreenUpdatesPublisher,
    private val sceneController: SceneController,
    private val sceneDispatcher: SceneDispatcher
) : ScreenUpdatesSubscriber {
    private val logger = Logger.getLogger<GLSurfaceController>()

    init {
        screenUpdatesPublisher.subscribe(this)
    }

    /**
     * Warning this method does all invocations at separated thread!!!
     * Used for physics updates
     */
    fun updateAsync() {
        sceneController.fixedUpdate()
    }

    override fun onScreenUpdate() {
        sceneController.update()
        renderFrame()
    }

    override fun onScreenInit() {
        sceneController.init()
    }

    override fun onScreenShow() {
        initGL()
    }

    override fun onScreenSizeChanged(width: Int, height: Int) {
        glViewport(0, height, width, 0)
    }

    override fun onScreenDestroy() {

    }

    private fun renderFrame() {
        val currentScene = sceneDispatcher.dispatchCurrent()
        if (currentScene != null) {

        }
    }

    private fun initGL() {
        GL.createCapabilities()

        glEnable(GL_TEXTURE_2D)
        glEnable(GL_DEPTH_TEST)

        glClearColor(0.0f, 0.85f, 0.85f, 1.0f)
    }
}