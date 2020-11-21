package com.digitalcrafts.kmmweather.shared

import org.koin.core.module.Module
import org.koin.dsl.module
import java.util.*

actual object PlatformUtils {
    actual fun getRandomUUID(): String = UUID.randomUUID().toString()
    actual fun getCurrentTimeInMills(): Long = Calendar.getInstance().timeInMillis
}

actual val platformModule: Module = module {

    /*single<Settings> {
        AndroidSettings(get())
    }*/
}