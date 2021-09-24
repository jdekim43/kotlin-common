package kr.jadekim.common.crypto

import com.soywiz.krypto.Padding
import com.soywiz.krypto.SecureRandom

actual val AES: Crypto = Crypto(
    encrypt = { data, key, iv ->
        val initialVector = iv ?: if (key.size > 16) key.sliceArray(IntRange(0, 15)) else key

        com.soywiz.krypto.AES.encryptAesCbc(data, key, initialVector, Padding.PKCS7Padding)
    },
    decrypt = { data, key, iv ->
        val initialVector = iv ?: if (key.size > 16) key.sliceArray(IntRange(0, 15)) else key

        com.soywiz.krypto.AES.decryptAesCbc(data, key, initialVector, Padding.PKCS7Padding)
    }
)

actual val AES_RANDOM_IV: Crypto = Crypto(
    encrypt = { data, key, iv ->
        val initialVector = iv ?: ByteArray(128).apply { SecureRandom.nextBytes(this) }
        val result = com.soywiz.krypto.AES.encryptAesCbc(data, key, initialVector, Padding.PKCS7Padding)

        initialVector + result
    },
    decrypt = { data, key, _ ->
        val blockSize = 128
        val initialVector = data.copyOfRange(0, blockSize)
        val cipherBytes = data.copyOfRange(blockSize, data.size)
        val original = com.soywiz.krypto.AES.decryptAesCbc(cipherBytes, key, initialVector, Padding.PKCS7Padding)

        var lastLength = original.size
        for (i in original.size - 1 downTo original.size - blockSize + 1) {
            if (original[i] == 0.toByte()) {
                lastLength--
            } else {
                break
            }
        }

        original.sliceArray(0 until lastLength)
    }
)

actual val RSA_2048: Crypto = Crypto(
    encrypt = { _, _, _ ->
        TODO()
    },
    decrypt = { _, _, _ ->
        TODO()
    }
)
