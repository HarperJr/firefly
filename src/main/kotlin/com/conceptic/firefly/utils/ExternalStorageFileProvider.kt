package com.conceptic.firefly.utils

import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.nio.charset.Charset

interface FileProvider {
    fun provideFile(fileName: String): File

    fun provideFileContent(fileName: String): ByteArray

    fun createFile(fileName: String, content: String): Boolean

    fun createFile(fileName: String, content: ByteArray): Boolean
}

object ExternalStorageFileProvider : FileProvider {
    @Throws(IllegalArgumentException::class)
    override fun provideFile(fileName: String): File {
        val file = File(fileName)
        return if (file.isFile && file.exists()) file
        else throw IllegalArgumentException("File $fileName doesn't exist")
    }

    override fun provideFileContent(fileName: String): ByteArray {
        val file = provideFile(fileName)
        return file.inputStream().use {
            with(BufferedInputStream(it)) { readBytes() }
        }
    }

    override fun createFile(fileName: String, content: String): Boolean {
        return createFile(fileName, content.toByteArray(Charset.defaultCharset()))
    }

    @Throws(IllegalArgumentException::class)
    override fun createFile(fileName: String, content: ByteArray): Boolean {
        val file = File(fileName)
        return if (file.isFile && !file.exists()) {
            val fileCreated = file.createNewFile()
            if (fileCreated) {
                file.outputStream().use {
                    with(BufferedOutputStream(it)) { write(content) }
                }
                true
            } else false
        } else throw IllegalArgumentException("File $fileName already exists")
    }
}

object ResourcesFileProvider : FileProvider {
    private val classLoader = ClassLoader::class.java.classLoader

    override fun provideFile(fileName: String): File {
        val resource = classLoader.getResource(fileName)
        return resource?.let {
            val file = File(it.file)
            return if (file.isFile && file.exists()) file
            else throw IllegalArgumentException("File $fileName doesn't exist")
        } ?: throw IllegalArgumentException("Resource by $fileName doesn't exist")
    }

    override fun provideFileContent(fileName: String): ByteArray {
        val file = provideFile(fileName)
        return file.inputStream().use {
            with(BufferedInputStream(it)) { readBytes() }
        }
    }

    override fun createFile(fileName: String, content: String): Boolean {
        return ResourcesFileProvider.createFile(fileName, content.toByteArray(Charset.defaultCharset()))
    }

    override fun createFile(fileName: String, content: ByteArray): Boolean {
        throw IllegalStateException("Resources store is readable only")
    }
}