package com.conceptic.firefly.app.gl.renderable.view.pane

import com.conceptic.firefly.app.gl.renderable.view.View
import java.nio.FloatBuffer
import java.nio.IntBuffer

class Pane private constructor(
    name: String,
    vertices: FloatBuffer,
    texCoordinates: FloatBuffer,
    elements: IntBuffer
) : View(name, vertices, texCoordinates, elements) {
    class Creator {
        fun create(name: String, left: Float, top: Float, right: Float, bottom: Float, color: Int): Pane {
            return Pane(
                name = name,
                vertices = FloatBuffer.wrap(floatArrayOf(left, top, 0f, right, top, 0f, right, bottom, 0f, left, bottom, 0f)),
                texCoordinates = FloatBuffer.wrap(floatArrayOf(0f, 1f, 1f, 1f, 1f, 0f, 0f, 0f)),
                elements = IntBuffer.wrap(intArrayOf(0, 1, 2, 0, 2, 1))
            )
        }
    }
}