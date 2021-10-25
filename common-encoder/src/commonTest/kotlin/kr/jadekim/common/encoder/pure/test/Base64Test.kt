package kr.jadekim.common.encoder.pure.test

import kr.jadekim.common.encoder.pure.Base64
import kotlin.test.Test
import kotlin.test.assertEquals

class Base64Test {

    @Test
    fun succeed() {
        val original = "testText1$"

        val encoded = Base64.encodeToString(original)
        assertEquals("dGVzdFRleHQxJA==", encoded)

        val decoded = Base64.decodeToString(encoded)
        assertEquals(original, decoded)
    }
}