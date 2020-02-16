package com.conceptic.firefly.screen

interface MouseActionListener {
    fun onClicked(x: Int, y: Int)

    fun onDoubleClicked(x: Int, y: Int)

    fun onMoved(x: Int, y: Int)
}