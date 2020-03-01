package com.conceptic.firefly.app.gl

import com.conceptic.firefly.utils.FileProvider

abstract class ContentProvider(private val fileProvider: FileProvider) {
    abstract val dir: String

    open fun provide(fileName: String): ByteArray {
        return fileProvider.provideFileContent(dir + fileName)
    }
}