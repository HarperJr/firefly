package com.conceptic.firefly.app.screen.listener

interface MouseActionsListener {
    fun onClicked(x: Int, y: Int)

    fun onDoubleClicked(x: Int, y: Int)

    fun onMoved(x: Int, y: Int)
}