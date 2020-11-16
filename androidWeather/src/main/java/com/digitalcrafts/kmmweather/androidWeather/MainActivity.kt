package com.digitalcrafts.kmmweather.androidWeather

import android.os.Bundle
import com.digitalcrafts.kmmweather.androidWeather.common.arch.BaseActivity


class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
