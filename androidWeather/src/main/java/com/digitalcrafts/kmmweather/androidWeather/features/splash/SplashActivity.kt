package com.digitalcrafts.kmmweather.androidWeather.features.splash

import android.os.Bundle
import com.digitalcrafts.kmmweather.androidWeather.R
import com.digitalcrafts.kmmweather.androidWeather.common.arch.BaseActivity

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }
}