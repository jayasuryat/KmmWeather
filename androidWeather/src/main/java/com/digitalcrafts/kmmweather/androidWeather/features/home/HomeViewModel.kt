package com.digitalcrafts.kmmweather.androidWeather.features.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.digitalcrafts.kmmweather.androidWeather.common.arch.BaseViewModel
import com.digitalcrafts.kmmweather.androidWeather.common.utils.LocationProvider
import com.digitalcrafts.kmmweather.shared.models.RemoteResponse
import com.digitalcrafts.kmmweather.shared.models.WeatherData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class HomeViewModel(context: Application) : BaseViewModel(context) {

    private val _obsWeatherData: MutableLiveData<WeatherData> = MutableLiveData()
    val obsWeatherData: LiveData<WeatherData> = _obsWeatherData

    private val _obsCoordinates: MutableLiveData<Pair<Double, Double>> = MutableLiveData()
    val obsCoordinates: LiveData<Pair<Double, Double>> = _obsCoordinates

    private val _obsDescription: MutableLiveData<String> = MutableLiveData()
    val obsDescription: LiveData<String> = _obsDescription

    val obsIsDataLoading: MutableLiveData<Boolean> = MutableLiveData(true)

    val obsError: MutableLiveData<String> = MutableLiveData()

    init {
        ioScope.launch { loadWeatherData() }
    }

    private suspend fun loadWeatherData() {

        val cachedData = repoPreferences.getWeatherData()

        if (cachedData != null) {
            _obsWeatherData.postValue(cachedData)
            _obsCoordinates.postValue(repoPreferences.getLatLong())
            return
        }

        val cachedCoordinates: Pair<Double, Double>? = repoPreferences.getLatLong()

        if (cachedCoordinates != null) {
            getWeatherData(cachedCoordinates)
            return
        }

        listenForLocationUpdates()
    }

    private suspend fun listenForLocationUpdates() {

        var trial = 0

        while (trial <= LOCATION_REQUEST_MAX_TRY) {

            val currentCoordinates: Pair<Double, Double>? = LocationProvider.getLastKnownLatLong()

            if (currentCoordinates != null) {
                getWeatherData(currentCoordinates)
                return
            }

            delay(LOCATION_REQUEST_DELAY)
            trial++
        }
    }

    fun reloadWeatherData() {
        val coordinates = _obsCoordinates.value ?: return
        obsIsDataLoading.postValue(true)
        ioScope.launch { getWeatherData(coordinates) }
    }

    private suspend fun getWeatherData(coordinates: Pair<Double, Double>) {

        _obsCoordinates.postValue(coordinates)

        fun updateWeatherData(weatherData: WeatherData) {
            obsError.postValue(null)
            _obsWeatherData.postValue(weatherData)
            repoPreferences.saveLatLong(coordinates)
            repoPreferences.saveWeatherData(weatherData)
        }

        when (val weatherData = repoWeather.getWeatherData(coordinates.first, coordinates.second)) {

            is RemoteResponse.Success -> updateWeatherData(weatherData.data)

            is RemoteResponse.Failure -> {
                val data = repoPreferences.getWeatherData()
                if (data == null) obsError.postValue(weatherData.error.message)
                else updateWeatherData(data)
            }
        }
    }

    fun bakeDescription(weatherData: WeatherData) {

        val description = weatherData.weather.firstOrNull()?.descr
        if (description.isNullOrEmpty()) return
        val formattedDescription = description.split(' ')
                .joinToString(" ") { it.capitalize(Locale.getDefault()) }
        _obsDescription.postValue(formattedDescription)
    }

    companion object {

        private const val LOCATION_REQUEST_DELAY: Long = 5 * 1000
        private const val LOCATION_REQUEST_MAX_TRY: Int = 6
    }
}