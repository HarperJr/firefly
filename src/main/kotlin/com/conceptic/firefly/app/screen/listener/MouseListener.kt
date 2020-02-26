package com.conceptic.firefly.app.screen.listener

interface MouseListener {
    fun onClicked(x: Int, y: Int)

    fun onPressed(x: Int, y: Int)

    fun onMoved(x: Int, y: Int)
}