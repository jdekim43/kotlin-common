package kr.jadekim.common.hash

expect val MD5: HashFunction

fun ByteArray.md5() = hash(MD5)

fun String.md5() = hash(MD5)


expect val HMAC_SHA_1: HashFunction

fun ByteArray.hmacSha1() = hash(HMAC_SHA_1)

fun String.hmacSha1() = hash(HMAC_SHA_1)


expect val HMAC_SHA_256: HashFunction

fun ByteArray.hmacSha256(key: ByteArray) = hash(HMAC_SHA_256, key)

fun String.hmacSha256(key: ByteArray) = hash(HMAC_SHA_256, key)


expect val HMAC_SHA_512: HashFunction

fun ByteArray.hmacSha512(key: ByteArray) = hash(HMAC_SHA_512, key)

fun String.hmacSha512(key: ByteArray) = hash(HMAC_SHA_512, key)


expect val SHA_256: HashFunction

fun ByteArray.sha256() = hash(SHA_256)

fun String.sha256() = hash(SHA_256)