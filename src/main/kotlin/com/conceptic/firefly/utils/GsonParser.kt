package com.conceptic.firefly.utils

import com.conceptic.firefly.app.gl.support.vec.Vector3
import com.conceptic.firefly.support.GsonAdapters
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import kotlin.reflect.KType
import kotlin.reflect.jvm.javaType

interface JsonParser {
    fun <T> fromJson(type: KType, json: String): T

    fun <T> toJson(value: T): String
}

object GsonParser : JsonParser {
    private val gson = GsonBuilder()
        .serializeNulls()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .registerTypeAdapter(Vector3::class.java, GsonAdapters.vector3Adapter)
        .create()

    override fun <T> fromJson(type: KType, json: String): T = gson.fromJson(json, type.javaType)

    override fun <T> toJson(value: T): String = gson.toJson(value)
}