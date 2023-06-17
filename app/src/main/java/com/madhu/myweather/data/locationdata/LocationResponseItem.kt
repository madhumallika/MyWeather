package com.madhu.myweather.data.locationdata

data class LocationResponseItem(
    val country: String,
    val lat: Double,
    val lon: Double,
    val name: String,
    val state: String
)