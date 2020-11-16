package com.digitalcrafts.kmmweather.androidWeather.features.home

import androidx.lifecycle.ViewModelProvider
import com.digitalcrafts.kmmweather.androidWeather.R
import com.digitalcrafts.kmmweather.androidWeather.common.arch.BaseAbstractFragment
import com.digitalcrafts.kmmweather.androidWeather.common.arch.ViewModelFactory
import com.digitalcrafts.kmmweather.androidWeather.databinding.FragmentHomeBinding


class HomeFragment : BaseAbstractFragment<HomeViewModel, FragmentHomeBinding>(R.layout.fragment_home) {

    override fun setViewModel(): HomeViewModel =
            ViewModelProvider(this@HomeFragment, ViewModelFactory {
                HomeViewModel(requireActivity().application)
            }).get(HomeViewModel::class.java)

    override fun setupViews(): FragmentHomeBinding.() -> Unit = {}

    override fun setupObservers(): HomeViewModel.() -> Unit = {}
}