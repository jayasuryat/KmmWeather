package com.digitalcrafts.kmmweather.shared.data.definitions

import com.digitalcrafts.kmmweather.shared.models.RemoteResponse
import com.digitalcrafts.kmmweather.shared.models.WeatherData

abstract class DataSourceWeather {

    abstract suspend fun getWeatherData(lat: Double, long: Double): RemoteResponse<WeatherData>
}