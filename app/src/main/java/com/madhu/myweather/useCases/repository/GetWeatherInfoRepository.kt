package com.madhu.myweather.useCases.repository

import com.madhu.myweather.data.WeatherResponse
import com.madhu.myweather.network.GetLocationService
import com.madhu.myweather.network.GetWeatherInfoService

class GetWeatherInfoRepository(
    private val getWeatherInfoService: GetWeatherInfoService,
    private val getLocationService: GetLocationService
) {

    private suspend fun getCurrentWeather(
        latitude: Double, longitude: Double
    ): WeatherResponse {
        return getWeatherInfoService.getWeatherData(latitude, longitude)
    }

    suspend fun getCurrentWeather(cityName: String): WeatherResponse {
        val locationResponse = getLocationService.getLocation(cityName)
        return getCurrentWeather(locationResponse[0].lat, locationResponse[0].lon)
    }
}
