package kr.jadekim.common.util

expect fun generateUUID(): String

expect fun currentTimeMillis(): Long

/**
 * -k=value
 * --key=value
 */
fun parseArgument(vararg args: String): Map<String, List<String>> {
    val result = mutableMapOf<String, MutableList<String>>()

    for (arg in args) {
        val tokens = arg.split("=")

        if (tokens.size != 2) {
            continue
        }

        val key = tokens[0]
        val value = tokens[1]

        if (key.startsWith("--")) {
            result.getOrPut(key.substring(2)) { mutableListOf() }.add(value)
            continue
        }

        if (key.startsWith("-")) {
            result.getOrPut(key.substring(1)) { mutableListOf() }.add(value)
            continue
        }
    }

    return result
}