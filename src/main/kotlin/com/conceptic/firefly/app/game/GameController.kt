package com.conceptic.firefly.app.game

import com.conceptic.firefly.Publishers
import com.conceptic.firefly.app.menu.MenuController
import com.conceptic.firefly.log.Logger
import com.conceptic.firefly.app.screen.Key
import com.conceptic.firefly.app.screen.support.*
import org.lwjgl.opengl.GL11.*

class GameController(
    publishers: Publishers
) : ScreenUpdatesSubscriberAdapter(), KeyActionsSubscriber, MouseActionsSubscriber {
    private val logger = Logger.getLogger<GameController>()

    private val screenUpdatesPublisher = publishers.screenUpdatesPublisher
    private val mouseActionsPublisher = publishers.mouseUpdatesPublisher
    private val keyActionsPublisher = publishers.keyActionsPublisher

    private var isInMainMenu = true

    private val menuController: MenuController = MenuController()

    fun init() {
        screenUpdatesPublisher.subscribe(this)
        mouseActionsPublisher.subscribe(this)
        keyActionsPublisher.subscribe(this)
    }

    override fun onShow(width: Int, height: Int) {
        menuController.onShow(width, height)
    }

    override fun onSizeChanged(width: Int, height: Int) {
        menuController.onSizeChanged(width, height)
    }

    override fun onUpdate() {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
        // Render something here
        if (isInMainMenu)
            menuController.onUpdate()
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