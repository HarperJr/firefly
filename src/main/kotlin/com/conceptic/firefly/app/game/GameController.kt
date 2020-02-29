package com.conceptic.firefly.app.game

import com.conceptic.firefly.Publishers
import com.conceptic.firefly.app.screen.Key
import com.conceptic.firefly.app.screen.support.KeyActionsSubscriber
import com.conceptic.firefly.app.screen.support.MouseActionsSubscriber
import com.conceptic.firefly.log.Logger

class GameController(
    publishers: Publishers
) : KeyActionsSubscriber, MouseActionsSubscriber {
    private val logger = Logger.getLogger<GameController>()
    private val mouseActionsPublisher = publishers.mouseActionsPublisher
    private val keyActionsPublisher = publishers.keyActionsPublisher

    fun onShow() {
        mouseActionsPublisher.subscribe(this)
        keyActionsPublisher.subscribe(this)
    }

    fun onUpdate() {

    }

    /**
     * Warning this method does all invocations at separated thread!!!
     * Used for physics updates
     */
    fun onFixedUpdate() {

    }

    fun onDestroy() {

    }

    fun onSizeChanged(width: Int, height: Int) {

    }

    override fun onPressed(key: Key) {

    }

    override fun onReleased(key: Key) {

    }

    override fun onClicked(x: Float, y: Float) {

    }

    override fun onPressed(x: Float, y: Float) {

    }

    override fun onMoved(x: Float, y: Float) {

    }
}