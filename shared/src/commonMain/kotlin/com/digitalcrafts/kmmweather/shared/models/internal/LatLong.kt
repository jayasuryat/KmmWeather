package com.digitalcrafts.kmmweather.shared.models.internal

import kotlinx.serialization.Serializable

@Serializable
data class LatLong(val lat: Long, val long: Long) {

    companion object {
        fun LatLong.toLatLon(): Pair<Long, Long> = Pair(this.lat, this.long)
        fun Pair<Long, Long>.fromLatLon(): LatLong = LatLong(this.first, this.second)
    }
}