package com.conceptic.firefly.app.gl

import com.conceptic.firefly.app.camera.CameraController
import com.conceptic.firefly.app.gl.renderable.view.button.Button
import com.conceptic.firefly.screen.support.MouseActionsPublisher
import com.conceptic.firefly.screen.support.MouseActionsSubscriber

class MenuController(
    private val cameraController: CameraController,
    private val mouseActionsPublisher: MouseActionsPublisher
): MouseActionsSubscriber {
    private val buttons: List<Button> = listOf()
    private var focusedButton: Button? = null

    private var mouseX: Float = 0f
    private var mouseY: Float = 0f


    override fun onMouseMove(mouseX: Float, mouseY: Float) {
        this.mouseX = mouseX
        this.mouseY = mouseY

        focusedButton = null
        buttons.forEach {
            if (it.instersect(mouseX, mouseX))
                focusedButton = it
        }
    }

    override fun onRightClick() {

    }

    override fun onLeftLick() {
        focusedButton?.let { it.onClickListener?.onClick(it) }
    }

    fun init() {
        mouseActionsPublisher.subscribe(this)
        cameraController.cameraSettings = CameraController.CameraSettings.Builder
            .isPerspectiveProjection(false)
            .zArguments(-1000f, 1000f)
            .build()
    }

    fun update() {
        cameraController.update()

    }
}
