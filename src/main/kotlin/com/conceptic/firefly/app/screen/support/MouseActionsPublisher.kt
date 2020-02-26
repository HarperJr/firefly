package com.conceptic.firefly.app.screen.support

import com.conceptic.firefly.app.screen.listener.MouseListener
import com.conceptic.firefly.support.Publisher

interface MouseActionsSubscriber : MouseListener

class MouseActionsPublisher : Publisher<MouseActionsSubscriber>(), MouseListener {
    override fun onClicked(x: Int, y: Int) {
        notify { onClicked(x, y) }
    }

    override fun onPressed(x: Int, y: Int) {
        notify { onPressed(x, y) }
    }

    override fun onMoved(x: Int, y: Int) {
        notify { onMoved(x, y) }
    }
}