package com.conceptic.firefly.app.game.obj.ui

import com.conceptic.firefly.app.gl.shader.Shader
import com.conceptic.firefly.app.screen.listener.MouseListener

interface UIEntity : MouseListener {
    val name: String

    fun create()

    fun render(shader: Shader, width: Float, height: Float)

    fun destroy()

    companion object {
        const val MATCH_PARENT = -1
        const val WRAP_CONTENT = -1
    }
}
