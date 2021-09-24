package kr.jadekim.common.encoder

import kr.jadekim.common.extension.utf8

interface Encoder {

    fun encode(data: ByteArray): ByteArray

    fun encode(data: String): ByteArray = encode(data.utf8())

    fun encodeToString(data: ByteArray): String = encode(data).utf8()

    fun encodeToString(data: String): String = encodeToString(data.utf8())

    fun decode(data: ByteArray): ByteArray

    fun decode(data: String): ByteArray = decode(data.utf8())

    fun decodeToString(data: ByteArray): String = data.utf8()

    fun decodeToString(data: String): String = decode(data).utf8()
}

fun ByteArray.encode(encoder: Encoder) = encoder.encode(this)

fun String.encode(encoder: Encoder) = encoder.encode(this)

fun ByteArray.encodeToString(encoder: Encoder) = encoder.encodeToString(this)

fun String.encodeToString(encoder: Encoder) = encoder.encodeToString(this)

fun ByteArray.decode(encoder: Encoder) = encoder.decode(this)

fun String.decode(encoder: Encoder) = encoder.decode(this)

fun ByteArray.decodeToString(encoder: Encoder) = encoder.decodeToString(this)

fun String.decodeToString(encoder: Encoder) = encoder.decodeToString(this)

class EncoderException(cause: Throwable?) : RuntimeException(cause?.message, cause)

internal fun Encoder(
    encode: (ByteArray) -> ByteArray,
    decode: (ByteArray) -> ByteArray
) = object : Encoder {

    override fun encode(data: ByteArray): ByteArray = try {
        encode(data)
    } catch (e: Exception) {
        throw EncoderException(e)
    }

    override fun decode(data: ByteArray): ByteArray = try {
        decode(data)
    } catch (e: Exception) {
        throw EncoderException(e)
    }
}
