package com.elouyi.util


@Suppress("NO_ACTUAL_FOR_EXPECT")
expect val utilLogger: YwLogger

operator fun String.times(n: Int) = repeat(n)

inline fun String.startsWith(vararg prefixes: String,action: (String) -> Unit) {
    for (prefix in prefixes) {
        if (startsWith(prefix)) {
            action(prefix)
            return
        }
    }
}