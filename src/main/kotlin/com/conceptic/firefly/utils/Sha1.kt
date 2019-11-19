package com.conceptic.firefly.utils

import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


object Sha1 {
    @Throws(NoSuchAlgorithmException::class, UnsupportedEncodingException::class)
    fun encode(input: String): String = with(MessageDigest.getInstance("SHA1")) {
        reset()
        update(input.toByteArray(Charset.defaultCharset()))

        val hexStringBuilder = StringBuilder()
        digest().forEach { digestByte ->
            hexStringBuilder.append(
                Integer.toString((digestByte.toInt() and 0xff) + 0x100, 16).substring(1)
            )
        }
        return hexStringBuilder.toString()
    }
}