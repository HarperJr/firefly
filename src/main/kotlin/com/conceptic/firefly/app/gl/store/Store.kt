package com.conceptic.firefly.app.gl.store

abstract class Store<T, U> {
    private val store = mutableMapOf<T, U>()

    protected abstract fun create(key: T): U

    protected abstract fun clear(key: T, element: U): Boolean

    fun newInstance(key: T) = create(key)
        .also { store[key] = it }

    fun remove(t: T) = store.remove(t)

    fun contains(t: T) = store.containsKey(t)

    fun clear() = store.forEach { t, u ->
        if (clear(t, u))
            store.remove(t)
    }

    operator fun get(key: T): U = store[key] ?: throw IllegalStateException("No element associated to key = $key")
}