package com.conceptic.firefly.app.screen.support

import com.conceptic.firefly.app.screen.listener.ScreenListener
import com.conceptic.firefly.support.Publisher

interface ScreenUpdatesSubscriber: ScreenListener

class ScreenUpdatesPublisher : Publisher<ScreenUpdatesSubscriber>(), ScreenListener {
    override fun onShow() {
        notify { onShow() }
    }

    override fun onUpdate() {
        notify { onUpdate() }
    }

    override fun onDestroy() {
        notify { onDestroy() }
    }

    override fun onSizeChanged(width: Int, height: Int) {
        notify { onSizeChanged(width, height) }
    }
}