package com.conceptic.firefly.screen

interface KeyActionListener {
    fun onPressed(key: Key)

    fun onReleased(key: Key)
}