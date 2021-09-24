package kr.jadekim.common.util

import kotlin.random.Random

actual class SecureRandom actual constructor(seed: ByteArray?) : Random() {

    private val random = if (seed == null) java.security.SecureRandom() else java.security.SecureRandom(seed)

    override fun nextBits(bitCount: Int): Int = random.nextInt().ushr(32 - bitCount) and (-bitCount).shr(31)
}