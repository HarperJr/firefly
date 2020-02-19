package com.conceptic.firefly.app.gl.renderable.view.button

import com.conceptic.firefly.app.gl.renderable.view.View
import com.conceptic.firefly.app.gl.texture.Texture

class Button constructor(
    name: String, left: Float, top: Float, right: Float, bottom: Float, texture: Texture
) : View(name, left, top, right, bottom, texture)