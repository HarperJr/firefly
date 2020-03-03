package com.conceptic.firefly.app.game.obj.ui.impl

import com.conceptic.firefly.app.game.obj.ui.OnClickListener
import com.conceptic.firefly.app.game.obj.ui.UIEntity
import com.conceptic.firefly.app.gl.phisix.AABB
import com.conceptic.firefly.app.gl.shader.Shader
import com.conceptic.firefly.app.gl.shader.view.ViewShader
import com.conceptic.firefly.app.gl.support.vec.Vector4
import com.conceptic.firefly.app.gl.view.View

class Button(
    override val name: String,
    private val text: String,
    private val width: Int,
    private val height: Int,
    color: Long
) : UIEntity {
    private val accentColorVec: Vector4 = Vector4.fromBytes(color)
    private val darkAccentColorVec: Vector4 = accentColorVec - 0.1f
    private val darkColorVec: Vector4 = accentColorVec - 0.25f

    private val currentColor: Vector4
        get() = when {
            isHovered -> darkColorVec
            isPressed -> darkAccentColorVec
            else -> darkAccentColorVec
        }

    var onClickListener: OnClickListener? = null

    private val buttonView = View(AABB(0f, height.toFloat(), width.toFloat(), 0f))
    private var isHovered = false
    private var isPressed = false

    override fun create() {
        buttonView.create()
    }

    override fun render(shader: Shader, width: Float, height: Float) {
        buttonView.apply {
            shader.uniformMat4(Shader.U_MODEL_MATRIX, buttonView.modelTransform)
            shader.uniformVec4(ViewShader.U_COLOR, currentColor)

            render()
        }
    }

    override fun destroy() {
        buttonView.destroy()
    }

    override fun onClicked(x: Float, y: Float) {
        isPressed = buttonView.aabb.intersect(x, y)
        if (isPressed)
            onClickListener?.onClick(this)
    }

    override fun onPressed(x: Float, y: Float) {
        isPressed = buttonView.aabb.intersect(x, y)
    }

    override fun onMoved(x: Float, y: Float) {
        isHovered = buttonView.aabb.intersect(x, y)
    }
}
