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

    fun dispatch(consumer: (T) -> Unit): AsyncOperationsDisposable {
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

        return AsyncOperationsDisposable(this)
    }

    fun dispose() {
        disposed.set(true)
    }
}

class AsyncOperationsDisposable(private val asyncOperation: AsyncOperation<*>?) {
    private var disposed = false

    fun dispose() {
        if (!disposed) {
            asyncOperation?.dispose()
            disposed = true
        }
    }

    companion object {
        fun disposed() = AsyncOperationsDisposable(null)
            .apply { disposed = true }
    }
}

fun <T> runAsync(blockingFunc: () -> T): AsyncOperation<T> {
    return AsyncOperation(blockingFunc)
}
