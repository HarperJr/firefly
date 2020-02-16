package com.conceptic.firefly.app.gl

import com.conceptic.firefly.app.gl.shader.loader.ShaderLoader
import com.conceptic.firefly.log.Logger
import com.conceptic.firefly.screen.support.ScreenUpdatesPublisher
import com.conceptic.firefly.screen.support.ScreenUpdatesSubscriber
import com.conceptic.firefly.screen.support.ScreenUpdatesSubscriberAdapter
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*

class GLController(
    private val screenUpdatesPublisher: ScreenUpdatesPublisher,
    private val menuController: MenuController
) : ScreenUpdatesSubscriberAdapter() {
    private val logger = Logger.getLogger<GLController>()

    private var sceneIsLoaded = false

    fun init() {
        screenUpdatesPublisher.subscribe(this)
    }

    /**
     * Warning this method does all invocations at separated thread!!!
     * Used for physics updates
     */
    fun updateAsync() {

    }

    override fun onUpdate() {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)

        if (sceneIsLoaded) {

        } else menuController.update()
    }

    override fun onShow() {
        GL.createCapabilities()
    }

    override fun onSizeChanged(width: Int, height: Int) {
        glViewport(0, height, width, 0)
    }

    override fun onDestroy() {

    }
}