package com.conceptic.firefly.app.screen.support

import com.conceptic.firefly.app.screen.Key
import com.conceptic.firefly.app.screen.listener.KeysListener
import com.conceptic.firefly.support.Publisher

interface KeyActionsSubscriber : KeysListener

class KeyActionsPublisher : Publisher<KeyActionsSubscriber>(), KeysListener {
    override fun onPressed(key: Key) {
        notify { onPressed(key) }
    }

    override fun onReleased(key: Key) {
        notify { onReleased(key) }
    }
}