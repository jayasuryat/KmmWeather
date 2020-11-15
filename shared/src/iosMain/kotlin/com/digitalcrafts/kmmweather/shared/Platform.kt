package com.digitalcrafts.kmmweather.shared

import platform.Foundation.NSDate
import platform.Foundation.NSUUID
import platform.Foundation.timeIntervalSince1970

actual object PlatformUtils {
    actual fun getRandomUUID(): String = NSUUID().UUIDString
    actual fun getCurrentTimeInMills(): Long = (NSDate().timeIntervalSince1970 * 1000).toLong()
}