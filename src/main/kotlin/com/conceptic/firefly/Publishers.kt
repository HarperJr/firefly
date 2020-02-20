package com.conceptic.firefly

import com.conceptic.firefly.app.screen.support.KeyActionsPublisher
import com.conceptic.firefly.app.screen.support.MouseActionsPublisher
import com.conceptic.firefly.app.screen.support.ScreenUpdatesPublisher

class Publishers {
    private var screenUpdatesPublisherInstance: ScreenUpdatesPublisher? = null
    val screenUpdatesPublisher: ScreenUpdatesPublisher
        get() = synchronized(this) {
            if (screenUpdatesPublisherInstance == null)
                screenUpdatesPublisherInstance = ScreenUpdatesPublisher()
            screenUpdatesPublisherInstance!!
        }

    val keyActionsPublisher: KeyActionsPublisher
        get() = synchronized(this) {
            if (keyActionsPublisherInstance == null)
                keyActionsPublisherInstance = KeyActionsPublisher()
            keyActionsPublisherInstance!!
        }
    private var keyActionsPublisherInstance: KeyActionsPublisher? = null

    val mouseUpdatesPublisher: MouseActionsPublisher
        get() = synchronized(this) {
            if (mouseUpdatesPublisherInstance == null)
                mouseUpdatesPublisherInstance = MouseActionsPublisher()
            mouseUpdatesPublisherInstance!!
        }
    private var mouseUpdatesPublisherInstance: MouseActionsPublisher? = null
}