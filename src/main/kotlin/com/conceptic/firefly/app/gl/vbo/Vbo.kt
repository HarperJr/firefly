package com.conceptic.firefly.app.gl.vbo

data class Vbo(
    val glPointer: Int,
    val type: VboType
)

enum class VboType {
    VERTEX, COLOR, INDEX
}