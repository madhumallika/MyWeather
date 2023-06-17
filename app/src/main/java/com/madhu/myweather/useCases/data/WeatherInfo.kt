package com.madhu.myweather.useCases.data

data class WeatherInfo(
    val temperature: String,
    val weatherDescription: String,
    val highTemperature: String,
    val lowTemperature: String,
    val humidity: Int
)
