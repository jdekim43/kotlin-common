package kr.jadekim.common.dsl

inline fun <T> blockOrNull(predicate: Boolean, block: () -> T): T? = if (predicate) {
    block()
} else {
    null
}