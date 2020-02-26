package com.conceptic.firefly.app.screen.listener

import com.conceptic.firefly.app.screen.Key

interface KeysListener {
    fun onPressed(key: Key)

    fun onReleased(key: Key)
}