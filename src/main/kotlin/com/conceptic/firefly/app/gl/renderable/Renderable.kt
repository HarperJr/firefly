package com.conceptic.firefly.app.gl.renderable

import java.nio.FloatBuffer

abstract class Renderable {
    abstract val name: String
    abstract val vertices: FloatBuffer
    abstract val verticesCount: Int
}