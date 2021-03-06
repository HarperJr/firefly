package com.conceptic.firefly.support

import com.conceptic.firefly.app.gl.support.vec.Vector3
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter

/**
 * Don't know if it works, but we possibly redefine serialized data representation at the near future
 */
object GsonAdapters {
    val vector3Adapter = object : TypeAdapter<Vector3?>() {
        override fun write(out: JsonWriter, value: Vector3?) {

        }

        override fun read(`in`: JsonReader): Vector3? {
            return with(`in`) {
                val components = mutableListOf<Float>()
                val jsonToken = peek()
                if (jsonToken == JsonToken.BEGIN_ARRAY) {
                    beginArray()
                    while (peek() != JsonToken.END_ARRAY)
                        components.add(nextDouble().toFloat())
                    endArray()
                }
                Vector3(
                    components.getOrNull(0) ?: 0f,
                    components.getOrNull(1) ?: 0f,
                    components.getOrNull(2) ?: 0f
                )
            }
        }
    }
}