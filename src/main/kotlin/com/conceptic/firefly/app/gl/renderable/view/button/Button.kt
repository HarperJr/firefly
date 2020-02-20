package com.conceptic.firefly.app.gl.renderable.view.button

import com.conceptic.firefly.app.gl.renderable.view.View
import com.conceptic.firefly.app.gl.support.Bounds
import com.conceptic.firefly.app.gl.support.Vector4
import com.conceptic.firefly.app.gl.texture.Texture

class Button constructor(
    name: String, bounds: Bounds, color: Vector4, texture: Texture
) : View(name, bounds, color, texture)