package com.digitalcrafts.kmmweather.androidWeather.common.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import android.os.Build
import android.provider.Settings
import com.digitalcrafts.kmmweather.androidWeather.common.Constants.DATE_DISPLAY_FORMAT
import com.digitalcrafts.kmmweather.shared.models.Weather
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    fun isLocationEnabled(context: Context): Boolean {

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            (context.getSystemService(Context.LOCATION_SERVICE) as? LocationManager)?.isLocationEnabled == true

        } else {
            // This is Deprecated in API 28
            val mode: Int = Settings.Secure.getInt(context.contentResolver, Settings.Secure.LOCATION_MODE, Settings.Secure.LOCATION_MODE_OFF)
            mode != Settings.Secure.LOCATION_MODE_OFF
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun getDisplayTime(time: Long): String =
            SimpleDateFormat(DATE_DISPLAY_FORMAT).format(Date(time * 1000))

}
