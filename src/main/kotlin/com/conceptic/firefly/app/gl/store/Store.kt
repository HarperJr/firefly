package com.conceptic.firefly.app.gl.store

abstract class Store<T, U> {
    private val store = mutableMapOf<T, U>()

    abstract fun create(key: T): U

    abstract fun clear(key: T, element: U): Boolean

    fun get(key: T) = store[key] ?: create(key)

    fun clear() = store.forEach { t, u ->
        if (clear(t, u)) {
            store.remove(t)
        }
    }
}