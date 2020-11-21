package com.digitalcrafts.kmmweather.shared.models

sealed class RemoteResponse<T> {

    abstract val isSuccess: Boolean

    class Success<T>(val data: T) : RemoteResponse<T>() {
        override val isSuccess: Boolean = true
    }

    class Failure<T>(val error: Throwable) : RemoteResponse<T>() {
        override val isSuccess: Boolean = false
    }

    companion object {
        fun <T> success(data: T): RemoteResponse<T> = Success(data)
        fun <T> failure(error: Throwable): RemoteResponse<T> = Failure(error)
    }
}

