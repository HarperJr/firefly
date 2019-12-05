package com.conceptic.firefly.app.gl.renderer.factory

import com.conceptic.firefly.app.gl.mesh.Mesh
import com.conceptic.firefly.app.gl.renderer.Renderable
import com.conceptic.firefly.app.gl.renderer.Renderer
import com.conceptic.firefly.app.gl.renderer.factory.mesh.MeshRendererFactory
import com.conceptic.firefly.app.gl.renderer.factory.view.ViewRendererFactory
import com.conceptic.firefly.app.gl.shader.ShaderStore
import com.conceptic.firefly.app.gl.vao.VaoStore
import com.conceptic.firefly.app.gl.view.View
import kotlin.reflect.KClass

class RendererFactoriesProvider(
    private val shaderStore: ShaderStore,
    private val vaoStore: VaoStore
) {
    private val factories = mapOf<KClass<*>, RendererFactory<*>>(
        Mesh::class to MeshRendererFactory(shaderStore, vaoStore),
        View::class to ViewRendererFactory(shaderStore)
    )

    fun provide(kClass: KClass<*>): Renderer<out Renderable>? = factories[kClass]?.create()
}