package kr.jadekim.common.crypto

expect val AES: Crypto

fun ByteArray.encryptAes(key: ByteArray, iv: ByteArray? = null) = encrypt(AES, key, iv)

fun String.encryptAes(key: ByteArray, iv: ByteArray? = null) = encrypt(AES, key, iv)

fun ByteArray.decryptAes(key: ByteArray, iv: ByteArray? = null) = decrypt(AES, key, iv)


expect val AES_RANDOM_IV: Crypto

fun ByteArray.encryptAesRandomIv(key: ByteArray, iv: ByteArray? = null) = encrypt(AES_RANDOM_IV, key, iv)

fun String.encryptAesRandomIv(key: ByteArray, iv: ByteArray? = null) = encrypt(AES_RANDOM_IV, key, iv)

fun ByteArray.decryptAesRandomIv(key: ByteArray, iv: ByteArray? = null) = decrypt(AES_RANDOM_IV, key, iv)


expect val RSA_2048: Crypto

fun ByteArray.encryptRsa2048(key: ByteArray, iv: ByteArray? = null) = encrypt(RSA_2048, key, iv)

fun String.encryptRsa2048(key: ByteArray, iv: ByteArray? = null) = encrypt(RSA_2048, key, iv)

fun ByteArray.decryptRsa2048(key: ByteArray, iv: ByteArray? = null) = decrypt(RSA_2048, key, iv)
