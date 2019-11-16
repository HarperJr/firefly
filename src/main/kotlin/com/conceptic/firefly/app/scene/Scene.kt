package com.conceptic.firefly.app.scene

import com.conceptic.firefly.app.camera.CameraController
import com.conceptic.firefly.app.gl.mesh.Mesh

class Scene(
    private val cameraController: CameraController
) {
    private val meshes = mutableListOf<Mesh>()
}