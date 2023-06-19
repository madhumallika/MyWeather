package com.madhu.myweather.useCases.data

data class WeatherInfo(
    val name: String,
    val temperature: Double,
    val weatherDescription: String,
    val highTemperature: Double,
    val lowTemperature: Double,
    val humidity: Int,
    val icon: String
)
