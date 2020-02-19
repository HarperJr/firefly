package com.conceptic.firefly.screen.support

import com.conceptic.firefly.support.Publisher

interface ScreenUpdatesSubscriber {
    fun onInit()

    fun onShow(width: Int, height: Int)

    fun onSizeChanged(width: Int, height: Int)

    fun onUpdate()

    fun onDestroy()
}

class ScreenUpdatesPublisher : Publisher<ScreenUpdatesSubscriber>()