package com.conceptic.firefly.app.game

import com.conceptic.firefly.app.game.camera.Camera

abstract class SceneEntity : GameEntity {
    abstract val camera: Camera
}
