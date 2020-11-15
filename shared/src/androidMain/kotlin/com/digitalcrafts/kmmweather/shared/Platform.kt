package com.digitalcrafts.kmmweather.shared

import java.util.*

actual object PlatformUtils {
    actual fun getRandomUUID(): String = UUID.randomUUID().toString()
    actual fun getCurrentTimeInMills(): Long = Calendar.getInstance().timeInMillis
}