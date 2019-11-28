package com.conceptic.firefly.utils

import com.conceptic.firefly.app.gl.support.Vector3
import org.lwjgl.BufferUtils
import java.nio.FloatBuffer

object VectorUtils {
    fun bufferWrap(vecs: List<Vector3>): FloatBuffer {
        return BufferUtils.createFloatBuffer(vecs.size * Vector3.COMPONENTS)
            .apply {
                vecs.forEachIndexed { i, vec ->
                    put(i * Vector3.COMPONENTS + 0, vec.x)
                    put(i * Vector3.COMPONENTS + 0, vec.x)
                    put(i * Vector3.COMPONENTS + 0, vec.x)
                }
            }
    }
}
