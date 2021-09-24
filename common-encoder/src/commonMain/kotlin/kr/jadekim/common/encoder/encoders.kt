package kr.jadekim.common.encoder

import kr.jadekim.common.encoder.pure.Base64
import kr.jadekim.common.encoder.pure.Hex

val BASE64 = Base64

fun ByteArray.encodeBase64() = encodeToString(BASE64)

fun String.encodeBase64() = encodeToString(BASE64)

fun ByteArray.decodeBase64() = decode(BASE64)

fun String.decodeBase64() = decode(BASE64)

val HEX = Hex

fun ByteArray.encodeHex() = encodeToString(HEX)

fun String.encodeHex() = encodeToString(HEX)

fun ByteArray.decodeHex() = decode(HEX)

fun String.decodeHex() = decode(HEX)
