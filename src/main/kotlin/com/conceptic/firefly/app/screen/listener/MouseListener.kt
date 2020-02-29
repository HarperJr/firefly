package com.conceptic.firefly.app.screen.listener

interface MouseListener {
    fun onClicked(x: Float, y: Float)

    fun onPressed(x: Float, y: Float)

    fun onMoved(x: Float, y: Float)
}