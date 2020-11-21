package com.digitalcrafts.kmmweather.shared.data.definitions

import com.digitalcrafts.kmmweather.shared.models.WeatherData

interface DataSourcePreferences {

    fun saveLatLong(latLong: Pair<Double, Double>)
    fun getLatLong(): Pair<Double, Double>?

    fun saveWeatherData(weatherData: WeatherData)
    fun getWeatherData(): WeatherData?
}