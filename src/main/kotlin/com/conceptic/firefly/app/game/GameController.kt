package com.conceptic.firefly.app.game

import com.conceptic.firefly.Publishers
import com.conceptic.firefly.app.game.menu.Menu
import com.conceptic.firefly.app.screen.GLFWScreen
import com.conceptic.firefly.app.screen.Key
import com.conceptic.firefly.app.screen.support.KeyActionsSubscriber
import com.conceptic.firefly.app.screen.support.MouseActionsSubscriber
import com.conceptic.firefly.log.Logger

class GameController(
    screen: GLFWScreen,
    publishers: Publishers
) : KeyActionsSubscriber, MouseActionsSubscriber {
    private val logger = Logger.getLogger<GameController>()
    private val mouseActionsPublisher = publishers.mouseActionsPublisher
    private val keyActionsPublisher = publishers.keyActionsPublisher

    private val menu = Menu(screen)

    fun onShow() {
        mouseActionsPublisher.subscribe(this)
        keyActionsPublisher.subscribe(this)

        menu.create()
    }

    fun onUpdate() {
        menu.update()
    }

    /**
     * Warning this method does all invocations at separated thread!!!
     * Used for physics updates
     */
    fun onFixedUpdate() {

    }

    fun onDestroy() {
        menu.destroy()
    }

    fun onSizeChanged(width: Float, height: Float) {

    }

    override fun onPressed(key: Key) {

    }

    override fun onReleased(key: Key) {

    }

    override fun onClicked(x: Float, y: Float) {
        menu.onClicked(x, y)
    }

    override fun onPressed(x: Float, y: Float) {
        menu.onPressed(x, y)
    }

    override fun onMoved(x: Float, y: Float) {
        menu.onMoved(x, y)
    }
}