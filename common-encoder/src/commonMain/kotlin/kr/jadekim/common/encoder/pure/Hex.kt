package kr.jadekim.common.encoder.pure

import kr.jadekim.common.encoder.Encoder
import kr.jadekim.common.extension.utf8

object Hex : Encoder {

    private val CHARACTER_MAP = "0123456789ABCDEF".toCharArray()

    override fun encode(data: ByteArray): ByteArray = encodeToString(data).utf8()

    override fun encodeToString(data: ByteArray): String = buildString(data.size * 2) {
        for (b in data) {
            val byte = b.toInt() and 0xFF

            append(CHARACTER_MAP[byte shr 4 and 0xF])
            append(CHARACTER_MAP[byte and 0xF])
        }
    }

    override fun decode(data: ByteArray): ByteArray = decode(data.utf8())

    override fun decode(data: String): ByteArray {
        val result = ByteArray(data.length / 2)

        var i = 0
        for (n in result.indices) {
            val c0 = decodeHexDigit(data[i++])
            val c1 = decodeHexDigit(data[i++])

            result[n] = ((c0 shl 4) or c1).toByte()
        }

        return result
    }

    private fun decodeHexDigit(c: Char): Int = when (c) {
        in '0'..'9' -> c - '0'
        in 'a'..'f' -> (c - 'a') + 10
        in 'A'..'F' -> (c - 'A') + 10
        else -> error("Invalid hex digit '$c'")
    }
}