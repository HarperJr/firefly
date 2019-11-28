package com.conceptic.firefly.app.gl.texture

data class Texture(
    val name: String,
    val glPointer: Int
) {
    companion object {
        val NONE = Texture("None", -1)
    }
}