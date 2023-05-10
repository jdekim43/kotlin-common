package kr.jadekim.common.extension

fun ByteArray.toFixed(size: Int) = ByteArray(size) {
    val padLength = size - this.size

    if (padLength - it > 0) {
        0.toByte()
    } else {
        get(it - padLength)
    }
}