package com.conceptic.firefly.app.gl.renderable

import java.nio.FloatBuffer
import java.nio.IntBuffer

abstract class Renderable {
    abstract val name: String
    abstract val vertices: FloatBuffer
    abstract val texCoordinates: FloatBuffer
    abstract val elements: IntBuffer

    val isOptimized: Boolean
        get() = elements.hasRemaining()
}