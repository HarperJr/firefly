package com.conceptic.firefly.app.gl.renderable.view

import com.conceptic.firefly.app.gl.renderable.Renderable
import java.nio.FloatBuffer
import java.nio.IntBuffer

open class View(
    override val name: String,
    override val vertices: FloatBuffer,
    override val texCoordinates: FloatBuffer,
    override val elements: IntBuffer
) : Renderable()