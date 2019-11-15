package com.conceptic.firefly.log

import org.apache.logging.log4j.LogManager

interface Logger {
    fun debug(message: String?, vararg params: Any)

    fun info(message: String?, vararg params: Any)

    fun error(throwable: Throwable, vararg params: Any)

    fun fatal(throwable: Throwable, vararg params: Any)

    companion object {
        val DEFAULT_LOGGER = Log4JLogger
        inline fun <reified T> getLogger(tree: LoggerTree = LoggerTree.DEFAULT): Logger {
            val defaultTag = T::class.simpleName!!
            return when (tree) {
                LoggerTree.DEFAULT -> {
                    DEFAULT_LOGGER.provide(defaultTag)
                }
            }
        }
    }
}

interface LoggerProvider {
    fun provide(tag: String): Logger
}

enum class LoggerTree {
    DEFAULT
}

object Log4JLogger : LoggerProvider {
    override fun provide(tag: String): Logger {
        val logger = LogManager.getLogger(tag)
        return object : Logger {
            override fun debug(message: String?, vararg params: Any) = logger.debug(message, params)

            override fun info(message: String?, vararg params: Any) = logger.info(message, params)

            override fun error(throwable: Throwable, vararg params: Any) = logger.error(throwable.message, params)

            override fun fatal(throwable: Throwable, vararg params: Any) = logger.fatal(throwable.message, params)
        }
    }
}