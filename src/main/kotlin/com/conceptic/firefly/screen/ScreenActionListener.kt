package com.conceptic.firefly.screen

interface ScreenActionListener {
    fun onSizeChanged(width: Int, height: Int)

    fun onInit()

    fun onShow()

    fun onUpdate()

    fun onDestroy()
}