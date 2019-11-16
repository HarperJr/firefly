package com.conceptic.firefly.app.camera

import com.conceptic.firefly.app.gl.support.Vector3
import com.conceptic.firefly.utils.MatrixUtils
import org.lwjgl.opengl.GL11.*

class Camera {
    private var position = Vector3.ZERO

    fun setupProjection(width: Int, height: Int) {
        val aspect = if (height != 0) width.toFloat() / height.toFloat() else 0f

        glMatrixMode(GL_PROJECTION)
        glLoadIdentity()

        MatrixUtils.setPerspective(PROJECTION_FOV, aspect, PROJECTION_NEAR, PROJECTION_FAR)
        glLoadMatrixf(MatrixUtils.projectionMatrixAsBuffer)

        glMatrixMode(GL_MODELVIEW)
    }

    fun move(vector: Vector3) {
        position = vector
    }

    fun update() {

    }

    companion object {
        private const val PROJECTION_FOV = 60f
        private const val PROJECTION_NEAR = 0f
        private const val PROJECTION_FAR = 3000f
    }
}