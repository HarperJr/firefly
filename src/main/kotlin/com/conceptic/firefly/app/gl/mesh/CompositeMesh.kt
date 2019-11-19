package com.conceptic.firefly.app.gl.mesh

import com.conceptic.firefly.utils.Sha1

class CompositeMesh(name: String, val children: List<Mesh>) : Mesh(name) {
    override val uniqueIndex: String
        get() = Sha1.encode(name + children.joinToString { it.name })
}