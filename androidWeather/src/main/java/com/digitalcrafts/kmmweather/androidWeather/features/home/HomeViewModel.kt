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

class HomeViewModel(context: Application) : BaseViewModel(context) {

    private val _obsCoordinates: MutableLiveData<Pair<Double, Double>> = MutableLiveData()
    val obsCoordinates: LiveData<Pair<Double, Double>> = _obsCoordinates

    val obsError: MutableLiveData<String> = MutableLiveData()

    private val _obsWeatherData: MutableLiveData<WeatherData> = MutableLiveData()
    val obsWeatherData: LiveData<WeatherData> = _obsWeatherData

    init {
        listenForLocationUpdated()
    }

    private fun listenForLocationUpdated() {

        ioScope.launch {

            while (true) {
                if (_obsCoordinates.value != null) return@launch
                _obsCoordinates.postValue(LocationProvider.getLastKnownLatLong())
                delay(LOCATION_REQUEST_DELAY)
            }
        }
    }

    fun getWeatherData(coordinates: Pair<Double, Double>) {

        ioScope.launch {

            when (val weatherData = repoWeather.getWeatherData(coordinates.first, coordinates.second)) {
                is RemoteResponse.Success -> {
                    obsError.postValue(null)
                    _obsWeatherData.postValue(weatherData.data)
                }
                is RemoteResponse.Failure -> obsError.postValue(weatherData.error.message)
            }
        }
    }

    companion object {

        private const val LOCATION_REQUEST_DELAY: Long = 5 * 1000
    }
}