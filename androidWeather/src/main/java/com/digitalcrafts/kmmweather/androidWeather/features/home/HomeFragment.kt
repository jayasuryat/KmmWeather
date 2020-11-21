package com.digitalcrafts.kmmweather.androidWeather.features.home

import androidx.lifecycle.ViewModelProvider
import coil.load
import com.digitalcrafts.kmmweather.androidWeather.R
import com.digitalcrafts.kmmweather.androidWeather.common.arch.BaseAbstractFragment
import com.digitalcrafts.kmmweather.androidWeather.common.arch.ViewModelFactory
import com.digitalcrafts.kmmweather.androidWeather.common.utils.isLocationEnabled
import com.digitalcrafts.kmmweather.androidWeather.databinding.FragmentHomeBinding
import com.digitalcrafts.kmmweather.shared.models.WeatherData


class HomeFragment : BaseAbstractFragment<HomeViewModel, FragmentHomeBinding>(R.layout.fragment_home) {

    override fun setViewModel(): HomeViewModel =
            ViewModelProvider(this@HomeFragment, ViewModelFactory {
                HomeViewModel(requireActivity().application)
            }).get(HomeViewModel::class.java)

    override fun setupViews(): FragmentHomeBinding.() -> Unit = {

        checkAndShowEnableLocationsMethod()
    }

    override fun setupObservers(): HomeViewModel.() -> Unit = {

        obsCoordinates.observeHere { coordinates ->
            coordinates?.let {
                getWeatherData(it)
            }
        }

        obsWeatherData.observeHere { weatherData ->
            weatherData?.let {
                val iconUrl = it.getIconUrl()
                if (!iconUrl.isNullOrEmpty()) mBinding.ivWeatherIcon.load(iconUrl)
            }
        }
    }

    private fun checkAndShowEnableLocationsMethod() {
        val message = if (isLocationEnabled(this@HomeFragment.requireContext())) null
        else "Please enable location"
        mViewModel.obsError.postValue(message)
    }

    private fun WeatherData.getIconUrl(): String? {
        val icon: String? = this.weather.firstOrNull()?.icon
        if (icon.isNullOrEmpty()) return null
        return "$IMAGE_ENDPOINT$icon$IMAGE_ENDPOINT_POST_FIX"
    }

    companion object {

        private const val IMAGE_ENDPOINT: String = "http://openweathermap.org/img/wn/"
        private const val IMAGE_ENDPOINT_POST_FIX: String = "@2x.png"
    }
}