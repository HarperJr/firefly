package com.conceptic.firefly.app.screen.listener

interface ScreenListener {
    fun onShow()

    fun onUpdate()

    fun onDestroy()

    fun onSizeChanged(width: Int, height: Int)
}