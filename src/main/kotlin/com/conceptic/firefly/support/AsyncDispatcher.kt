package com.conceptic.firefly.support

import java.util.*

typealias Operation = () -> Unit

class AsyncDispatcher {
    private val scheduledQueue = ArrayDeque<Operation>()

    fun dispatch() {
        while (true) {
            val scheduled = scheduledQueue.poll()
            if (scheduled != null) {
                scheduled.invoke()
            } else break
        }
    }

    fun schedule(operation: Operation) {
        scheduledQueue.push(operation)
    }

    companion object {
        private var instance: AsyncDispatcher? = null

        fun get(): AsyncDispatcher {
            return synchronized(this) {
                if (instance == null)
                    instance = AsyncDispatcher()
                instance!!
            }
        }
    }
}
