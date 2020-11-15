package com.digitalcrafts.kmmweather.shared.data.impl

import com.digitalcrafts.kmmweather.shared.data.definitions.DataSourceWeather
import com.digitalcrafts.kmmweather.shared.models.RemoteResponse
import com.digitalcrafts.kmmweather.shared.models.WeatherData
import io.ktor.client.*
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import kotlinx.serialization.json.Json

class DataSourceImplWeather : DataSourceWeather() {

    private val httpClient: HttpClient by lazy {
        HttpClient {
            install(JsonFeature) {
                val json = Json {
                    isLenient = true
                    encodeDefaults = true
                    ignoreUnknownKeys = true
                }
                serializer = KotlinxSerializer(json)
            }
        }
    }

    override suspend fun getWeatherData(lat: Double, long: Double): RemoteResponse<WeatherData> {

        val response = kotlin.runCatching {
            httpClient.get<WeatherData>(Routes.getRouteForLatLong(lat, long))
        }

        return if (response.isSuccess) RemoteResponse.success(response.getOrThrow())
        else RemoteResponse.failure(response.exceptionOrNull()!!)
    }
}