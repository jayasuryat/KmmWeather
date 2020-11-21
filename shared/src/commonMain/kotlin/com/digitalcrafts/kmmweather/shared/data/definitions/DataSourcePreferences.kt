package com.digitalcrafts.kmmweather.shared.data.definitions

import com.digitalcrafts.kmmweather.shared.models.WeatherData

abstract class DataSourcePreferences {

    abstract fun saveLatLong(latLong: Pair<Long, Long>)
    abstract fun getLatLong(): Pair<Long, Long>?

    abstract fun saveWeatherData(weatherData: WeatherData)
    abstract fun getWeatherData(): WeatherData?
}