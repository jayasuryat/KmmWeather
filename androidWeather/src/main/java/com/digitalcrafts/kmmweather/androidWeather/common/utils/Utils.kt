package com.digitalcrafts.kmmweather.androidWeather.common.utils

import android.content.Context
import android.location.LocationManager
import android.os.Build
import android.provider.Settings

fun isLocationEnabled(context: Context): Boolean {

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        (context.getSystemService(Context.LOCATION_SERVICE) as? LocationManager)?.isLocationEnabled == true

    } else {
        // This is Deprecated in API 28
        val mode: Int = Settings.Secure.getInt(context.contentResolver, Settings.Secure.LOCATION_MODE, Settings.Secure.LOCATION_MODE_OFF)
        mode != Settings.Secure.LOCATION_MODE_OFF
    }
}