package com.digitalcrafts.kmmweather.androidWeather

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.digitalcrafts.kmmweather.androidWeather.common.utils.LocationProvider
import com.digitalcrafts.kmmweather.shared.initKoin
import org.koin.core.module.Module
import org.koin.dsl.module

class KmmWeatherApplication : Application() {

    private val appModule: Module by lazy {
        module {
            single<Application> { this@KmmWeatherApplication }
            single<Context> { get<Application>() }
            /*single<SharedPreferences> {
                get<Context>().getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE)
            }*/
        }
    }

    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize() {
        LocationProvider.init(this)
        initKoin(appModule)
    }

    companion object {

        private const val SP_FILE_NAME: String = "WEATHER_APP_PREFS"
    }
}