package com.conceptic.firefly.app.game.menu

import com.conceptic.firefly.app.game.obj.ui.Canvas
import com.conceptic.firefly.app.game.obj.ui.UIEntity
import com.conceptic.firefly.app.game.obj.ui.impl.Button

class MenuCanvas : Canvas() {
    override fun inflateEntities(): List<UIEntity> {
        return listOf(
            Button(
                "startGameBtn",
                "Start The Game",
                100,
                100,
                0xff4f4fff
            )
        )
    }
}