package kr.jadekim.common.hash

import kr.jadekim.common.encoder.HEX
import kr.jadekim.common.encoder.encodeToString
import kr.jadekim.common.extension.utf8

interface HashFunction {

    fun hash(data: ByteArray, key: ByteArray? = null): ByteArray

    fun hash(data: String, key: ByteArray? = null): ByteArray = hash(data.utf8(), key)

    fun hashHex(data: ByteArray, key: ByteArray? = null): String = hash(data, key).encodeToString(HEX)

    fun hashHex(data: String, key: ByteArray? = null): String = hash(data, key).encodeToString(HEX)
}

class HashException(cause: Throwable?) : RuntimeException(cause?.message, cause)

internal fun HashFunction(body: (ByteArray, ByteArray?) -> ByteArray) = object : HashFunction {

    override fun hash(data: ByteArray, key: ByteArray?): ByteArray = try {
        body(data, key)
    } catch (e: Exception) {
        throw HashException(e)
    }
}

fun ByteArray.hash(function: HashFunction, key: ByteArray? = null) = function.hashHex(this, key)

fun String.hash(function: HashFunction, key: ByteArray? = null) = function.hashHex(this, key)
