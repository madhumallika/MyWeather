package com.madhu.myweather.data.repository

import com.madhu.myweather.data.response.WeatherResponse
import com.madhu.myweather.network.GetWeatherInfoService

class GetWeatherInfoRepository(
    private val getWeatherInfoService: GetWeatherInfoService
) {
    suspend fun getCurrentWeather(
        latitude: Double, longitude: Double
    ): WeatherResponse {
        return getWeatherInfoService.getWeatherData(latitude, longitude)
    }
}
