package com.conceptic.firefly.app.gl

import com.conceptic.firefly.log.Logger
import com.conceptic.firefly.screen.support.ScreenUpdatesPublisher
import com.conceptic.firefly.screen.support.ScreenUpdatesSubscriber
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*

class GLSurfaceController(
    private val screenUpdatesPublisher: ScreenUpdatesPublisher
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

    }

    override fun onScreenUpdate() {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
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
        glViewport(0, height, width, 0)
    }

    override fun onScreenDestroy() {

    }

    private fun initGL() {
        GL.createCapabilities()
        glClearColor(1f, 0f, 0f, 0f)
    }
}