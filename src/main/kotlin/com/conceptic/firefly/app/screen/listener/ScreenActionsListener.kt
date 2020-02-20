package com.conceptic.firefly.app.screen.listener

interface ScreenActionsListener {
    fun onSizeChanged(width: Int, height: Int)

    fun onShow(width: Int, height: Int)

    fun onInit()

    fun onUpdate()

    fun onDestroy()
}