package com.conceptic.firefly.support

open class Publisher<Subscriber> {
    private val subscribers = mutableListOf<Subscriber>()

    fun subscribe(subscriber: Subscriber) = subscribers.add(subscriber)

    fun unsubscribe(subscriber: Subscriber) = subscribers.remove(subscriber)

    fun notify(action: Subscriber.() -> Unit) = subscribers.forEach { action.invoke(it) }
}