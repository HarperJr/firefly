package com.conceptic.firefly.screen.support

import com.conceptic.firefly.support.Publisher

interface ScreenUpdatesSubscriber {
    fun onScreenInit()

    fun onScreenShow()

    fun onScreenSizeChanged(width: Int, height: Int)

    fun onScreenUpdate()

    fun onScreenDestroy()
}

class ScreenUpdatesPublisher : Publisher<ScreenUpdatesSubscriber>()