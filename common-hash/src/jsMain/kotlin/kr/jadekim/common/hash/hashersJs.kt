package kr.jadekim.common.hash

import com.soywiz.krypto.HMAC
import com.soywiz.krypto.SHA256

actual val MD5: HashFunction = HashFunction { data, _ ->
    com.soywiz.krypto.MD5.digest(data).bytes
}

actual val HMAC_SHA_1: HashFunction = HashFunction { data, key ->
    requireNotNull(key)

    HMAC.hmacSHA1(key, data).bytes
}

actual val HMAC_SHA_512: HashFunction = HashFunction { _, _ ->
    TODO()
}

actual val SHA_256: HashFunction = HashFunction { data, _ ->
    SHA256.digest(data).bytes
}
