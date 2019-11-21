package com.conceptic.firefly.app.gl.shader

import com.conceptic.firefly.utils.Sha1

data class Shader(val name: String, val program: Int) {
    val uniqueIndex
        get() = Sha1.encode(name)
}