package com.digitalcrafts.kmmweather.shared

import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSDate
import platform.Foundation.NSUUID
import platform.Foundation.timeIntervalSince1970

actual object PlatformUtils {

    actual fun getRandomUUID(): String = NSUUID().UUIDString
    actual fun getCurrentTimeInMills(): Long = (NSDate().timeIntervalSince1970 * 1000).toLong()

    fun initKoinIos() = initKoin(module {})
}

actual val platformModule: Module = module {}
