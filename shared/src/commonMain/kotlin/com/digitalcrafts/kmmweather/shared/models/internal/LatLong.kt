package com.digitalcrafts.kmmweather.shared.models.internal

import kotlinx.serialization.Serializable

@Serializable
data class LatLong(val lat: Double, val long: Double) {

    companion object {
        fun LatLong.toLatLon(): Pair<Double, Double> = Pair(this.lat, this.long)
        fun Pair<Double, Double>.fromLatLon(): LatLong = LatLong(this.first, this.second)
    }
}