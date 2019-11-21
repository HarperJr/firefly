package com.conceptic.firefly.app.gl.vbo

sealed class VboDefinition(val type: VboType)

data class VertexVbo(val index: String) : VboDefinition(VboType.VERTEX)
data class ColorVbo(val index: String) : VboDefinition(VboType.COLOR)
data class IndexVbo(val index: String) : VboDefinition(VboType.INDEX)