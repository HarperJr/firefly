package com.conceptic.firefly.support

import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.atomic.AtomicBoolean

class AsyncOperation<T>(private val blockingFunc: () -> T) {
    private val pooledExecutor = Executors.newSingleThreadExecutor()
    private val asyncFunc: () -> Future<T>
        get() = {
            pooledExecutor.submit(blockingFunc)
        }
    private val disposed = AtomicBoolean(false)

    fun then(consumer: (T) -> Unit): AsyncOperation<T> {
        val future = asyncFunc.invoke()
        pooledExecutor.submit {
            val result = future.get()
            if (!disposed.get()) {
                AsyncDispatcher.get().schedule {
                    consumer.invoke(result)
                    pooledExecutor.shutdown()
                }
            } else pooledExecutor.shutdown()
        }

        return this
    }

    fun dispose() {
        disposed.set(true)
    }
}

fun <T> loadAsync(blockingFunc: () -> T): AsyncOperation<T> {
    return AsyncOperation(blockingFunc)
}
