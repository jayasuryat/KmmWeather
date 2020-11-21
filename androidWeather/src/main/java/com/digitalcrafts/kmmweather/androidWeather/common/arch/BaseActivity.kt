package com.digitalcrafts.kmmweather.androidWeather.common.arch

import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

open class BaseActivity : AppCompatActivity() {

    protected val TAG: String = javaClass.simpleName

    private val mJobDelegate: Lazy<Job> = lazy { Job() }
    private val mJob: Job by mJobDelegate
    protected val uiScope: CoroutineScope by lazy { CoroutineScope(Dispatchers.Main + mJob) }
    protected val ioScope: CoroutineScope by lazy { CoroutineScope(Dispatchers.IO + mJob) }

    override fun onDestroy() {
        super.onDestroy()
        if (mJobDelegate.isInitialized()) mJob.cancel()
    }
}