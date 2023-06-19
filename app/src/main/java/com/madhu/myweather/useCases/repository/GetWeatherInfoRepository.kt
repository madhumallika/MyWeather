package com.madhu.myweather.useCases.repository

import com.madhu.myweather.data.WeatherResponse
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
