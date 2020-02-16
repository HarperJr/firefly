package com.conceptic.firefly.screen.support

import com.conceptic.firefly.screen.Key
import com.conceptic.firefly.support.Publisher

interface KeyActionsSubscriber {
    fun onPressed(key: Key)

    fun onReleased(key: Key)
}

class KeyActionsPublisher : Publisher<KeyActionsSubscriber>()