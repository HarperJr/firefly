package com.conceptic.firefly.log

import java.util.logging.Level

interface Logger {
    fun d(message: String?)

    fun i(message: String?)

    fun e(throwable: Throwable)

    companion object {
        val DEFAULT_LOGGER = JUtilsLogger
        inline fun <reified T> getLogger(): Logger {
            return DEFAULT_LOGGER.provide(T::class.simpleName!!)
        }
    }
}

interface LoggerProvider {
    fun provide(tag: String): Logger
}

object JUtilsLogger : LoggerProvider {
    override fun provide(tag: String): Logger {
        return object : Logger {
            private val logger = java.util.logging.Logger.getLogger(tag)

            override fun d(message: String?) = logger.log(Level.ALL, message)

            override fun i(message: String?) = logger.log(Level.INFO, message)

            override fun e(throwable: Throwable) = logger.log(Level.FINE, throwable.message)
        }
    }
}