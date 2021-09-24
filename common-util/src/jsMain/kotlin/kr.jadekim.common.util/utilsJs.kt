package kr.jadekim.common.util

import kotlin.js.Date
import kotlin.random.Random

actual fun generateUUID(): String = "xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx".replace(Regex("[xy]")) {
    val c = it.value.first()
    val r = (Random.nextDouble() * 16).toInt()
    val v = if (c == 'x') r else r and 0x3 or 0x8

    v.toString(16)
}

actual fun currentTimeMillis(): Long = Date.now().toLong()
