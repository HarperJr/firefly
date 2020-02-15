package com.conceptic.firefly.app.camera

import com.conceptic.firefly.app.gl.support.Vector3
import com.conceptic.firefly.utils.MatrixUtils
import org.lwjgl.opengl.GL11.*

class Camera {
    private var position = Vector3.ZERO
    private var isPerspectiveProjection = false
    private var zNear = -1f
    private var zFar = 1f
    private var fov = 60f

    fun move(vector: Vector3) {
        position = vector
    }

    fun update(width: Int, height: Int) {
        glMatrixMode(GL_PROJECTION)
        glLoadIdentity()

        if (isPerspectiveProjection) {
            val aspect = if (height != 0) width.toFloat() / height.toFloat() else 0f
            MatrixUtils.setPerspective(fov, aspect, zNear, zFar)
        } else MatrixUtils.setOrtho(0f, width.toFloat(), height.toFloat(), 0f, zNear, zFar)

        glMatrixMode(GL_MODELVIEW)
        glLoadIdentity()
        glTranslatef(position.x, position.y, position.z)
    }

    fun setSettings(isPerspectiveProjection: Boolean, zNear: Float, zFar: Float, fov: Float) {
        this.isPerspectiveProjection = isPerspectiveProjection
        this.zNear = zNear
        this.zFar = zFar
        this.fov = fov
    }
}