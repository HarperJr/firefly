package com.conceptic.firefly.app.gl.mesh

import com.conceptic.firefly.app.gl.mesh.material.Material
import com.conceptic.firefly.app.gl.support.Vector3
import com.conceptic.firefly.utils.Sha1

/**
 * This class represents any solid 3d mesh
 */
class SolidMesh(
    name: String, val vertices: List<Vector3>, val indexes: List<Int>,
    val texCoordinates: List<Vector3>, val normals: List<Vector3>, val material: Material
) : Mesh(name) {
    override val uniqueIndex: String
        get() = Sha1.encode(name)
}