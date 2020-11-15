package com.digitalcrafts.kmmweather.androidWeather.utils

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment

class PermissionManager(private val host: Fragment) {

    fun areAllPermissionsGranted(): Boolean = checkPermissions()

    fun requestAllPermissions(): Unit = takePermissions()

    private fun checkPermissions(): Boolean =
            Manifest.permission.ACCESS_FINE_LOCATION.isGranted() &&
                    Manifest.permission.ACCESS_COARSE_LOCATION.isGranted()

    private fun takePermissions() {
        host.requestPermissions(arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION), PERMISSION_REQUEST_CODE)
    }

    private fun String.isGranted(): Boolean =
            ActivityCompat.checkSelfPermission(host.requireContext(), this) == PackageManager.PERMISSION_GRANTED

    companion object {

        const val PERMISSION_REQUEST_CODE: Int = 100
    }
}