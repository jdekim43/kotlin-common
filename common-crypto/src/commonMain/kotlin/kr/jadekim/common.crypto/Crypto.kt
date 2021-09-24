package kr.jadekim.common.crypto

import kr.jadekim.common.encoder.BASE64
import kr.jadekim.common.encoder.Encoder
import kr.jadekim.common.extension.utf8

interface Crypto {

    fun encrypt(data: ByteArray, key: ByteArray, iv: ByteArray? = null): ByteArray

    fun encrypt(data: String, key: ByteArray, iv: ByteArray? = null): ByteArray = encrypt(data.utf8(), key, iv)

    fun decrypt(data: ByteArray, key: ByteArray, iv: ByteArray? = null): ByteArray

    fun decrypt(data: String, key: ByteArray, iv: ByteArray? = null): ByteArray = decrypt(data.utf8(), key, iv)

    fun encryptEncoded(data: ByteArray, key: ByteArray, iv: ByteArray? = null, encoder: Encoder = BASE64): ByteArray {
        return encoder.encode(encrypt(data, key, iv))
    }

    fun encryptEncoded(data: String, key: ByteArray, iv: ByteArray? = null, encoder: Encoder = BASE64): ByteArray {
        return encoder.encode(encrypt(data, key, iv))
    }

    fun decryptEncoded(data: ByteArray, key: ByteArray, iv: ByteArray? = null, encoder: Encoder = BASE64): ByteArray {
        return decrypt(encoder.decode(data), key, iv)
    }

    fun decryptEncoded(data: String, key: ByteArray, iv: ByteArray? = null, encoder: Encoder = BASE64): ByteArray {
        return decrypt(encoder.decode(data), key, iv)
    }

    fun encryptString(data: ByteArray, key: ByteArray, iv: ByteArray? = null, encoder: Encoder = BASE64): String {
        return encoder.encodeToString(encrypt(data, key, iv))
    }

    fun encryptString(data: String, key: ByteArray, iv: ByteArray? = null, encoder: Encoder = BASE64): String {
        return encoder.encodeToString(encrypt(data, key, iv))
    }

    fun decryptString(data: ByteArray, key: ByteArray, iv: ByteArray? = null, encoder: Encoder = BASE64): String {
        return decrypt(encoder.decode(data), key, iv).utf8()
    }

    fun decryptString(data: String, key: ByteArray, iv: ByteArray? = null, encoder: Encoder = BASE64): String {
        return decrypt(encoder.decode(data), key, iv).utf8()
    }
}

class CryptoException(cause: Throwable?) : RuntimeException(cause?.message, cause)

internal fun Crypto(
    encrypt: (ByteArray, ByteArray, ByteArray?) -> ByteArray,
    decrypt: (ByteArray, ByteArray, ByteArray?) -> ByteArray
) = object : Crypto {

    override fun encrypt(data: ByteArray, key: ByteArray, iv: ByteArray?): ByteArray = try {
        encrypt(data, key, iv)
    } catch (e: Exception) {
        throw CryptoException(e)
    }

    override fun decrypt(data: ByteArray, key: ByteArray, iv: ByteArray?): ByteArray = try {
        decrypt(data, key, iv)
    } catch (e: Exception) {
        throw CryptoException(e)
    }
}

fun ByteArray.encrypt(crypto: Crypto, key: ByteArray, iv: ByteArray? = null) = crypto.encrypt(this, key, iv)

fun String.encrypt(crypto: Crypto, key: ByteArray, iv: ByteArray? = null) = crypto.encrypt(this, key, iv)

fun ByteArray.decrypt(crypto: Crypto, key: ByteArray, iv: ByteArray? = null) = crypto.decrypt(this, key, iv)
