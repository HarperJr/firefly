package com.conceptic.firefly.app.gl.texture

import com.conceptic.firefly.utils.FileProvider
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11
import java.awt.image.BufferedImage
import java.nio.ByteBuffer
import javax.imageio.ImageIO


class TextureContentProvider private constructor(private val fileProvider: FileProvider) {
    fun provide(textureFileName: String): BufferedImage = ImageIO.read(fileProvider.provideFile(textureFileName))

    companion object {
        fun fromFileProvider(fileProvider: FileProvider) = TextureContentProvider(fileProvider)
    }
}

class TextureLoader(
    private val textureContentProvider: TextureContentProvider,
    private val textureStore: TextureStore
) {
    fun load(textureFileName: String): Texture {
        val textureImage = textureContentProvider.provide(textureFileName)
        val textureBuffer = textureImage.let { texture ->
            val pixelsArray = with(IntArray(size = texture.width * texture.height)) {
                texture.getRGB(0, 0, texture.width, texture.height, this, 0, texture.width)
            }
            val pixelsByteBuffer = BufferUtils.createByteBuffer(pixelsArray.size * BYTES_PER_PIXEL_RGBA)
            pixelsArray.forEachIndexed { i, pixel ->
                with(pixelsByteBuffer) {
                    put(i * BYTES_PER_PIXEL_RGBA + 0, (pixel shl 0x00 and 0xff).toByte())
                    put(i * BYTES_PER_PIXEL_RGBA + 1, (pixel shl 0x08 and 0xff).toByte())
                    put(i * BYTES_PER_PIXEL_RGBA + 2, (pixel shl 0x10 and 0xff).toByte())
                    put(i * BYTES_PER_PIXEL_RGBA + 3, (pixel shl 0x18 and 0xff).toByte())
                }
            }
            pixelsByteBuffer.flip() as ByteBuffer
        }

        val texture = textureStore.get(textureFileName)
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.glPointer)

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST)
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST)

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP)
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP)

        GL11.glTexImage2D(
            GL11.GL_TEXTURE_2D,
            0,
            GL11.GL_RGBA,
            textureImage.width,
            textureImage.height,
            0,
            GL11.GL_RGBA,
            GL11.GL_UNSIGNED_BYTE,
            textureBuffer
        )

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0)

        textureBuffer.clear()
        return texture
    }

    companion object {
        private const val BYTES_PER_PIXEL_RGBA = 4
    }
}