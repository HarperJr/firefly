package com.conceptic.firefly.app.game.menu

import com.conceptic.firefly.app.game.SceneEntity
import com.conceptic.firefly.app.game.camera.Camera
import com.conceptic.firefly.app.game.obj.PlayingEntity
import com.conceptic.firefly.app.gl.mesh.loader.MeshLoader
import com.conceptic.firefly.app.gl.support.vec.Vector3
import com.conceptic.firefly.app.screen.GLFWScreen
import com.conceptic.firefly.app.screen.listener.MouseListener
import com.conceptic.firefly.support.AsyncOperationsDisposable
import com.conceptic.firefly.support.runAsync
import com.conceptic.firefly.utils.FileProvider

class Menu(private val screen: GLFWScreen) : SceneEntity(), MouseListener {
    override val camera: Camera = Camera(true, fov = 75f, near = 0.1f, far = 32f)
    private val canvas = MenuCanvas()

    private val width: Float
        get() = screen.width.toFloat()
    private val height: Float
        get() = screen.height.toFloat()

    private var bgEntity: PlayingEntity? = null
    private var meshFetchDisposable = AsyncOperationsDisposable.disposed()

    override fun create() {
        camera.setPosition(Vector3(0f, 0f, -22.0f))

        canvas.create()
        meshFetchDisposable = runAsync {
            MeshLoader(FileProvider.get()).load(BG_MESH_FILE_PATH)
        }.dispatch { mesh ->
            bgEntity = PlayingEntity(this, mesh)
                .also { it.create() }
        }
    }

    override fun update() {
        camera.update(width, height)
        canvas.update(width, height)
        bgEntity?.update()
    }

    override fun destroy() {
        meshFetchDisposable.dispose()
        bgEntity?.destroy()
        canvas.destroy()
    }

    override fun onClicked(x: Float, y: Float) {
        canvas.onClicked(x, y)
    }

    override fun onPressed(x: Float, y: Float) {
        canvas.onPressed(x, y)
    }

    override fun onMoved(x: Float, y: Float) {
        canvas.onMoved(x, y)
    }

    companion object {
        private const val BG_MESH_FILE_PATH = "elementalist/Elementalist.obj"
    }
}