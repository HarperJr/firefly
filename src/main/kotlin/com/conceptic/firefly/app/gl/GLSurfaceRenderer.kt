package com.conceptic.firefly.app.gl

import com.conceptic.firefly.log.Logger
import com.conceptic.firefly.screen.support.ScreenUpdatesPublisher
import com.conceptic.firefly.screen.support.ScreenUpdatesSubscriber
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*

class GLSurfaceRenderer(
    private val screenUpdatesPublisher: ScreenUpdatesPublisher
) : ScreenUpdatesSubscriber {
    private val logger = Logger.getLogger<GLSurfaceRenderer>()

    init {
        screenUpdatesPublisher.subscribe(this)
    }

    /**
     * Warning this method does all invocations at separated thread!!!
     * Used for physics updates
     */
    fun updateAsync() {
        logger.debug("updateAsync")
    }

    /**
     * Init resources here
     */
    override fun onScreenInit() {

    }

    override fun onScreenShow() {
        initGL()
    }

    override fun onScreenSizeChanged(width: Int, height: Int) {

    }

    override fun onScreenUpdate() {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
        logger.debug("updateSync")
    }

    override fun onScreenDestroy() {

    }

    private fun initGL() {
        GL.createCapabilities()
        glClearColor(1f, 0f, 0f, 0f)
    }
}