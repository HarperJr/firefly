package com.conceptic.firefly.app.gl.view

import com.conceptic.firefly.app.gl.support.Vector2
import com.conceptic.firefly.app.gl.support.Vector4

class Area(
    override val vertices: List<Vector2>,
    override val colors: List<Vector4>
) : View()