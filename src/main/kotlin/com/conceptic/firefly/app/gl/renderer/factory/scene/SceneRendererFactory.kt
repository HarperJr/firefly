package com.conceptic.firefly.app.gl.renderer.factory.scene

import com.conceptic.firefly.app.gl.renderer.Renderer
import com.conceptic.firefly.app.gl.renderer.factory.RendererFactory
import com.conceptic.firefly.app.scene.Scene

class SceneRendererFactory() : RendererFactory<Scene> {
    override fun create(): Renderer<Scene> = SceneRenderer()
}