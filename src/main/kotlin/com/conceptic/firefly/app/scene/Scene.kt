package com.conceptic.firefly.app.scene

import com.conceptic.firefly.app.camera.CameraController
import com.conceptic.firefly.app.gl.mesh.Mesh
import com.conceptic.firefly.app.gl.renderer.SceneRenderer

class Scene(
    private val cameraController: CameraController,
    private val sceneRenderer: SceneRenderer
) {
    private val meshes = mutableListOf<Mesh>()

    /**
     * Load objects, textures, and other resources for this scene
     */
    fun init() {
    }
}