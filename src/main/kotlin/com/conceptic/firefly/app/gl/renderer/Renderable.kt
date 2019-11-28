package com.conceptic.firefly.app.gl.renderer

interface Renderable {
    /**
     * Optimized flag means that we should render this easiest way
     */
    val isOptimized: Boolean
}