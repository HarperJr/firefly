package com.conceptic.firefly.screen.listener

import com.conceptic.firefly.screen.Key

interface KeyActionListener {
    fun onPressed(key: Key)

    fun onReleased(key: Key)
}