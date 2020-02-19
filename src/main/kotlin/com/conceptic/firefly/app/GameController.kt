package com.conceptic.firefly.app

import com.conceptic.firefly.app.menu.MenuController
import com.conceptic.firefly.log.Logger
import com.conceptic.firefly.screen.Key
import com.conceptic.firefly.screen.support.*
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL11.*

class GameController(
    private val screenUpdatesPublisher: ScreenUpdatesPublisher,
    private val mouseActionsPublisher: MouseActionsPublisher,
    private val keyActionsPublisher: KeyActionsPublisher
) : ScreenUpdatesSubscriberAdapter(), KeyActionsSubscriber, MouseActionsSubscriber {
    private val logger = Logger.getLogger<GameController>()

    private var screenWidth: Int = 0
    private var screenHeight: Int = 0
    private var isInMainMenu = true

    private val menuController: MenuController = MenuController()

    fun init() {
        screenUpdatesPublisher.subscribe(this)
        mouseActionsPublisher.subscribe(this)
        keyActionsPublisher.subscribe(this)

        GL.createCapabilities()

        GL11.glClearColor(0.4f, 0.2f, 0.2f, 1.0f)

        GL11.glEnable(GL11.GL_TEXTURE_2D)
        GL11.glEnable(GL11.GL_DEPTH_TEST)
    }

    override fun onShow(width: Int, height: Int) {
        screenWidth = width
        screenHeight = height

        glViewport(0, screenWidth, screenHeight, 0)

        menuController.onShow(screenWidth, screenHeight)
    }

    override fun onSizeChanged(width: Int, height: Int) {
        screenWidth = width
        screenHeight = height

        glViewport(0, screenWidth, screenHeight, 0)
    }

    override fun onUpdate() {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
        // Render something here
        if (isInMainMenu)
            menuController.onUpdate(screenWidth, screenHeight)
    }

    override fun onDestroy() {

    }

    override fun onPressed(key: Key) {

    }

    override fun onReleased(key: Key) {

    }

    override fun onClicked(x: Int, y: Int) {
        menuController.onClicked(x, y)
    }

    override fun onDoubleClicked(x: Int, y: Int) {

    }

    override fun onMoved(x: Int, y: Int) {
        menuController.onMoved(x, y)
    }

    /**
     * Warning this method does all invocations at separated thread!!!
     * Used for physics updates
     */
    fun updateAsync() {

    }
}