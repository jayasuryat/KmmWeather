package com.digitalcrafts.kmmweather.shared.data.impl

import com.digitalcrafts.kmmweather.shared.data.definitions.DataSourcePreferences
import com.digitalcrafts.kmmweather.shared.models.WeatherData
import com.digitalcrafts.kmmweather.shared.models.internal.LatLong
import com.digitalcrafts.kmmweather.shared.models.internal.LatLong.Companion.fromLatLon
import com.digitalcrafts.kmmweather.shared.models.internal.LatLong.Companion.toLatLon
import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.core.KoinComponent
import org.koin.core.inject

class DataSourceImplPreferences : DataSourcePreferences(), KoinComponent {

    private val settings: Settings by inject()

    override fun saveLatLong(latLong: Pair<Long, Long>) =
            settings.set(PREF_KEY_LAT_LON, Json.encodeToString(latLong.fromLatLon()))

    override fun getLatLong(): Pair<Long, Long>? =
            settings.getObjectOrNull<LatLong>(PREF_KEY_LAT_LON)?.toLatLon()


    override fun saveWeatherData(weatherData: WeatherData) =
            settings.set(PREF_KEY_WEATHER_DATA, Json.encodeToString(weatherData))

    override fun getWeatherData(): WeatherData? =
            settings.getObjectOrNull(PREF_KEY_WEATHER_DATA)


    private inline fun <reified T : Any> Settings.getObjectOrNull(key: String): T? {
        val json = this.getStringOrNull(key)
        return if (json.isNullOrEmpty()) null
        else Json.decodeFromString(json)
    }

    companion object {

        private const val PREF_KEY_LAT_LON: String = "PREF_KEY_LAT_LON"
        private const val PREF_KEY_WEATHER_DATA: String = "PREF_KEY_WEATHER_DATA"
    }
}