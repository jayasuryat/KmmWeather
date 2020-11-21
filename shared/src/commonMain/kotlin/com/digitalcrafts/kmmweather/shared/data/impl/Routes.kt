package com.digitalcrafts.kmmweather.shared.data.impl

internal object Routes {

    private const val API_KEY: String = "11787f662822f9d52cf6391d083063b9"
    private const val UNITS: String = "metric"

    private const val WEATHER_API_BASE_URL: String = "https://api.openweathermap.org/data/2.5/weather?"

    private fun String.appendAPIKey(): String = "${this@appendAPIKey}&appid=$API_KEY"

    private fun String.appendUnits(): String = "${this}&units=${UNITS}"

    fun getRouteForLatLong(lat: Double, lon: Double): String = WEATHER_API_BASE_URL + "lat=$lat&lon=$lon".appendAPIKey().appendUnits()
}