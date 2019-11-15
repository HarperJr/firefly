package com.conceptic.firefly.app.camera

import com.conceptic.firefly.screen.support.ScreenUpdatesPublisher
import com.conceptic.firefly.screen.support.ScreenUpdatesSubscriberAdapter

class CameraController(
    private val screenUpdatesPublisher: ScreenUpdatesPublisher
) : ScreenUpdatesSubscriberAdapter() {
    private val camera = Camera()

    init {
        screenUpdatesPublisher.subscribe(this)
    }

    override fun onScreenUpdate() {

    }

    override fun onScreenSizeChanged(width: Int, height: Int) {
        camera.setupProjection(width, height)
    }
}