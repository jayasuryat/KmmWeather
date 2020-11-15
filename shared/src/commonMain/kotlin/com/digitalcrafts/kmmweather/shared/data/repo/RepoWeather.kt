package com.digitalcrafts.kmmweather.shared.data.repo

import com.digitalcrafts.kmmweather.shared.data.definitions.DataSourceWeather
import com.digitalcrafts.kmmweather.shared.data.impl.DataSourceImplWeather
import com.digitalcrafts.kmmweather.shared.models.RemoteResponse
import com.digitalcrafts.kmmweather.shared.models.WeatherData

class RepoWeather : DataSourceWeather() {

    private val dataSourceWeather: DataSourceWeather by lazy { DataSourceImplWeather() }

    override suspend fun getWeatherData(lat: Double, long: Double): RemoteResponse<WeatherData> =
            dataSourceWeather.getWeatherData(lat, long)
}