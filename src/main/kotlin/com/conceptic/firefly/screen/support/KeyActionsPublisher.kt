package com.conceptic.firefly.screen.support

import com.conceptic.firefly.screen.Keys
import com.conceptic.firefly.support.Publisher

interface KeyActionsSubscriber {
    fun onKeyPressed(key: Keys)

    fun onKeyReleased(key: Keys)
}

class KeyActionsPublisher : Publisher<KeyActionsSubscriber>()