package com.conceptic.firefly.app.game.obj

import com.conceptic.firefly.app.game.SceneEntity
import com.conceptic.firefly.app.gl.support.mat.Matrix4
import com.conceptic.firefly.app.gl.support.vec.Vector3

abstract class GameEntity : SceneEntity {
    val model: FloatArray
        get() = modelMatrix.toFloatArray()

    private val modelMatrix = Matrix4()
    private val position = Vector3.ZERO


    fun move(vec: Vector3) {
        position.translate(vec)
    }

    fun setPosition(vec: Vector3) {
        position.set(vec)
    }

    open fun update() {
        modelMatrix.translate(position)
        render()
    }
}
