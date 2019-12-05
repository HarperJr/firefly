package com.conceptic.firefly.app.gl.store

abstract class Store<T, U> {
    private val store = mutableMapOf<T, U>()

    protected abstract fun create(key: T): U

    protected abstract fun clear(key: T, element: U): Boolean

    fun get(key: T) = store[key] ?: create(key)
        .also { store[key] = it }

    fun contains(t: T) = store.containsKey(t)

    fun clear() = store.forEach { t, u ->
        if (clear(t, u)) {
            store.remove(t)
        }
    }
}