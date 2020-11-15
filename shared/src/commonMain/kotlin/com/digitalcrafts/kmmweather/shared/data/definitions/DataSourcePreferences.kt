package com.digitalcrafts.kmmweather.shared.data.definitions

abstract class DataSourcePreferences {

    abstract fun saveLatLong(): Pair<Long, Long>
    abstract fun getLatLong(): Pair<Long, Long>
}