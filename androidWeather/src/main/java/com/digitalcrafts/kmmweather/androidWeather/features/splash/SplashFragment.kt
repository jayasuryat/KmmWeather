package com.digitalcrafts.kmmweather.androidWeather.features.splash

import android.content.Intent
import android.content.pm.PackageManager
import androidx.lifecycle.ViewModelProvider
import com.digitalcrafts.kmmweather.androidWeather.MainActivity
import com.digitalcrafts.kmmweather.androidWeather.R
import com.digitalcrafts.kmmweather.androidWeather.common.arch.BaseAbstractFragment
import com.digitalcrafts.kmmweather.androidWeather.common.arch.ViewModelFactory
import com.digitalcrafts.kmmweather.androidWeather.common.ui.show
import com.digitalcrafts.kmmweather.androidWeather.common.utils.PermissionManager
import com.digitalcrafts.kmmweather.androidWeather.databinding.FragmentSplashBinding


class SplashFragment : BaseAbstractFragment<SplashViewModel, FragmentSplashBinding>(R.layout.fragment_splash) {

    private val permissionManager: PermissionManager by lazy { PermissionManager(this@SplashFragment) }

    override fun setViewModel(): SplashViewModel =
            ViewModelProvider(this@SplashFragment, ViewModelFactory {
                SplashViewModel(requireActivity().application)
            }).get(SplashViewModel::class.java)

    override fun setupViews(): FragmentSplashBinding.() -> Unit = {

        mBinding.btnRequestPermissions.setOnClickListener {
            permissionManager.requestAllPermissions()
        }

        navNext()
    }

    override fun setupObservers(): SplashViewModel.() -> Unit = {}

    private fun navNext() {

        fun navigate() {
            startActivity(Intent(this@SplashFragment.requireContext(), MainActivity::class.java))
            requireActivity().finish()
        }

        if (permissionManager.areAllPermissionsGranted()) navigate()
        else permissionManager.requestAllPermissions()
    }

    private fun showPermissionRationale() = mBinding.gPermissionsRejected.show()

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PermissionManager.PERMISSION_REQUEST_CODE) {

            val isPermissionGranted = grantResults.fold(true) { agg, current ->
                agg && current == PackageManager.PERMISSION_GRANTED
            }
            if (isPermissionGranted) navNext()
            else showPermissionRationale()
        }
    }
}