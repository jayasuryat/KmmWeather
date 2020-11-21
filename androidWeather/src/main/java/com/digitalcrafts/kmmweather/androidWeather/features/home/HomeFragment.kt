package com.digitalcrafts.kmmweather.androidWeather.features.home

import androidx.lifecycle.ViewModelProvider
import coil.load
import com.digitalcrafts.kmmweather.androidWeather.R
import com.digitalcrafts.kmmweather.androidWeather.common.arch.BaseAbstractFragment
import com.digitalcrafts.kmmweather.androidWeather.common.arch.ViewModelFactory
import com.digitalcrafts.kmmweather.androidWeather.common.utils.Utils.isLocationEnabled
import com.digitalcrafts.kmmweather.androidWeather.databinding.FragmentHomeBinding
import com.digitalcrafts.kmmweather.shared.models.WeatherData


class HomeFragment : BaseAbstractFragment<HomeViewModel, FragmentHomeBinding>(R.layout.fragment_home) {

    override fun setViewModel(): HomeViewModel =
            ViewModelProvider(this@HomeFragment, ViewModelFactory {
                HomeViewModel(requireActivity().application)
            }).get(HomeViewModel::class.java)

    override fun setupViews(): FragmentHomeBinding.() -> Unit = {

        ivRefresh.setOnClickListener { mViewModel.reloadWeatherData() }
    }

    override fun setupObservers(): HomeViewModel.() -> Unit = {

        obsWeatherData.observeHere { weatherData ->

            obsIsDataLoading.postValue(false)

            if (weatherData != null) {

                bakeDescription(weatherData)

                val iconUrl = weatherData.getIconUrl()
                if (!iconUrl.isNullOrEmpty()) mBinding.ivWeatherIcon.load(iconUrl)

            } else checkIsLocationEnabled()
        }
    }

    private fun checkIsLocationEnabled() {
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