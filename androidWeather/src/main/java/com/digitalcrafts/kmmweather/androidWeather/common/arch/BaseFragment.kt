package com.digitalcrafts.kmmweather.androidWeather.common.arch

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

open class BaseFragment : Fragment() {

    protected val TAG: String = javaClass.simpleName

    private val mJobDelegate: Lazy<Job> = lazy { Job() }
    private val mJob: Job by mJobDelegate
    protected val uiScope: CoroutineScope by lazy { CoroutineScope(Dispatchers.Main + mJob) }
    protected val ioScope: CoroutineScope by lazy { CoroutineScope(Dispatchers.IO + mJob) }

    fun <T : Any> LiveData<T>.observeHere(onChange: (T?) -> Unit) {
        this.observe(viewLifecycleOwner, { data ->
            data?.let { onChange.invoke(it) }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mJobDelegate.isInitialized()) mJob.cancel()
    }
}