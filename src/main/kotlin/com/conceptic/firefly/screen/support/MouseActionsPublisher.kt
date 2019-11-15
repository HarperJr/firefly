package com.conceptic.firefly.screen.support

import com.conceptic.firefly.support.Publisher

interface MouseActionsSubscriber {
    fun onMouseMove(mouseX: Int, mouseY: Float)

    fun onRightClick()

    fun onLeftLick()
}

class MouseActionsPublisher : Publisher<MouseActionsSubscriber>()