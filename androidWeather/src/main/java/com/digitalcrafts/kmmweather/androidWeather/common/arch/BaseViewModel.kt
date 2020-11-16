package com.digitalcrafts.kmmweather.androidWeather.common.arch

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

open class BaseViewModel(context: Application) : AndroidViewModel(context) {

    protected val TAG: String = javaClass.simpleName

    private val mJobDelegate: Lazy<Job> = lazy { Job() }
    private val mJob: Job by mJobDelegate
    protected val ioScope: CoroutineScope by lazy { CoroutineScope(Dispatchers.IO + mJob) }

    override fun onCleared() {
        super.onCleared()
        if (mJobDelegate.isInitialized()) mJob.cancel()
    }
}