package com.digitalcrafts.kmmweather.shared

import com.russhwolf.settings.Settings
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

fun initKoin(appModule: Module) {

    val koinApplication = startKoin {
        modules(
                appModule,
                coreModule,
                platformModule
        )
    }
}

private val coreModule: Module = module {

    single { Settings() }
}

expect val platformModule: Module