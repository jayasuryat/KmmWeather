package com.digitalcrafts.kmmweather.androidWeather.utils

import android.Manifest
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat


object LocationProvider : LocationListener {

    private const val MIN_DISTANCE_BEFORE_UPDATE: Float = 1f // in meters
    private const val MIN_TIME_BW_UPDATES: Long = 1000L

    private lateinit var context: Context
    private var mLastKnownLocation: Location? = null

    fun init(context: Context) {
        this.context = context
        startListeningForLocationUpdates()
    }

    fun getLastKnownLatLong(): Pair<Double, Double>? {
        mLastKnownLocation?.let { lastKnownLocation ->
            return Pair(lastKnownLocation.latitude, lastKnownLocation.longitude)
        } ?: return null
    }

    private fun startListeningForLocationUpdates() {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "Location permissions not granted.", Toast.LENGTH_SHORT).show()
            return
        }

        (context.getSystemService(LOCATION_SERVICE) as LocationManager?)?.run {
            requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_BEFORE_UPDATE, this@LocationProvider)
            requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_BEFORE_UPDATE, this@LocationProvider)
        }
    }

    override fun onLocationChanged(location: Location?) {
        if (location?.isBetterLocation(mLastKnownLocation) == true)
            mLastKnownLocation = location
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) = Unit
    override fun onProviderEnabled(provider: String?) = Unit
    override fun onProviderDisabled(provider: String?) = Unit

    /** Determines whether one Location reading is better than the current Location fix
     * Extension : The new Location that you want to evaluate
     * @param currentBestLocation  The current Location fix, to which you want to compare the new one
     */
    private fun Location.isBetterLocation(currentBestLocation: Location?): Boolean {

        /** Checks whether two providers are the same  */
        fun isSameProvider(provider1: String?, provider2: String?): Boolean {
            return if (provider1 == null) {
                provider2 == null
            } else provider1 == provider2
        }

        val location: Location = this

        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true
        }

        // Check whether the new location fix is newer or older
        val timeDelta = location.elapsedRealtimeNanos - currentBestLocation.elapsedRealtimeNanos
        val isSignificantlyNewer = timeDelta > MIN_TIME_BW_UPDATES
        val isSignificantlyOlder = timeDelta < -MIN_TIME_BW_UPDATES
        val isNewer = timeDelta > 0

        // If it's been more than two minutes since the current location, use the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true
            // If the new location is more than two minutes older, it must be worse
        } else if (isSignificantlyOlder) {
            return false
        }

        // Check whether the new location fix is more or less accurate
        val accuracyDelta = (location.accuracy - currentBestLocation.accuracy).toInt()
        val isLessAccurate = accuracyDelta > 0
        val isMoreAccurate = accuracyDelta < 0
        val isSignificantlyLessAccurate = accuracyDelta > 200

        // Check if the old and new location are from the same provider
        val isFromSameProvider = isSameProvider(location.provider, currentBestLocation.provider)

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true
        } else if (isNewer && !isLessAccurate) {
            return true
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true
        }
        return false
    }
}