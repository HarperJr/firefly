package com.conceptic.firefly.app.screen.support

import com.conceptic.firefly.app.screen.listener.MouseListener
import com.conceptic.firefly.support.Publisher

interface MouseActionsSubscriber : MouseListener

class MouseActionsPublisher : Publisher<MouseActionsSubscriber>(), MouseListener {
    override fun onClicked(x: Float, y: Float) {
        notify { onClicked(x, y) }
    }

    override fun onPressed(x: Float, y: Float) {
        notify { onPressed(x, y) }
    }

    override fun onMoved(x: Float, y: Float) {
        notify { onMoved(x, y) }
    }
}