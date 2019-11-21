package com.conceptic.firefly.app.gl.store

abstract class Store<T, U> {
    private val store = mutableMapOf<T, U>()

    abstract fun create(index: T): U

    abstract fun clear(index: T, element: U)

    fun get(index: T) = store[index] ?: create(index)

    fun clear() = store.forEach { t, u -> clear(t, u) }
}