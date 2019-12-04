package com.conceptic.firefly.app.scene

enum class SceneState {
    /**
     * The scene is initiated and all resources for it have to be loaded
     */
    CREATED,
    /**
     * The scene is completely loaded and ready to be resumed
     */
    STARTED,
    /**
     * The scene now interacts to user and runs all scripts are needed
     */
    RESUMED,
    /**
     * The scene now paused and doesn't able to interact with user's actions, but could be resumed again
     */
    PAUSED,
    /**
     * The scene is getting destroyed, all objects have to be destroyed and all resources are flushed
     */
    DESTROYED
}