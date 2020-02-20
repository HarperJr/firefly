package com.conceptic.firefly.app.screen.listener

import com.conceptic.firefly.app.screen.Key

interface KeyActionsListener {
    fun onPressed(key: Key)

    fun onReleased(key: Key)
}