package com.digitalcrafts.kmmweather.shared

expect object PlatformUtils {
    fun getRandomUUID(): String
    fun getCurrentTimeInMills(): Long
}