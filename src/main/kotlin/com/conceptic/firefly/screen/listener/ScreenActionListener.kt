package com.conceptic.firefly.screen.listener

interface ScreenActionListener {
    fun onSizeChanged(width: Int, height: Int)

    fun onShow(width: Int, height: Int)

    fun onInit()

    fun onUpdate()

    fun onDestroy()
}