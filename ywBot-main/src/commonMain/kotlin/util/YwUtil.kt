package com.elouyi.util


@Suppress("NO_ACTUAL_FOR_EXPECT")
expect val utilLogger: YwLogger

operator fun String.times(n: Int) = repeat(n)