package kr.jadekim.common.util

fun Int.radix(radix: Int, characters: CharArray) = buildString {
    if (radix > characters.size) {
        throw IllegalArgumentException("Too large radix (support max ${characters.size})")
    }

    var value = this@radix

    while (value != 0) {
        append(characters[value % radix])
        value /= radix
    }

    reverse()
}

fun Long.radix(radix: Int, characters: CharArray) = buildString {
    if (radix > characters.size) {
        throw IllegalArgumentException("Too large radix (support max ${characters.size})")
    }

    var value = this@radix

    while (value != 0L) {
        append(characters[(value % radix).toInt()])
        value /= radix
    }

    reverse()
}