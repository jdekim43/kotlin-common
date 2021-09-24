package kr.jadekim.common.util

import kotlin.random.Random

actual class SecureRandom actual constructor(seed: ByteArray?) : Random() {

    private val random = com.soywiz.krypto.SecureRandom

    override fun nextBits(bitCount: Int): Int = random.nextBits(bitCount)
}
