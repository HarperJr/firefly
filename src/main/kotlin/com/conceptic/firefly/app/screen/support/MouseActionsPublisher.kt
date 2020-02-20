package com.conceptic.firefly.app.screen.support

import com.conceptic.firefly.support.Publisher

interface MouseActionsSubscriber {
    fun onClicked(x: Int, y: Int)

    fun onDoubleClicked(x: Int, y: Int)

    fun onMoved(x: Int, y: Int)
}

class MouseActionsPublisher : Publisher<MouseActionsSubscriber>()