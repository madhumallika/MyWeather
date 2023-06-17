package com.madhu.myweather.useCases.repository

import com.madhu.myweather.network.GetLocationService
import com.madhu.myweather.network.GetWeatherInfoService

class GetWeatherInfoRepository(
    val getWeatherInfoService: GetWeatherInfoService,
    val getLocationService: GetLocationService
) {

    private suspend fun getCurrentWeather(latitude: Double, longtitude: Double) {
        val weatherResponse = getWeatherInfoService.getWeatherData(latitude, longtitude)
    }

    suspend fun getCurrentWeather(cityName: String) {
        val locationResponse = getLocationService.getLocation(cityName)
        getCurrentWeather(locationResponse[0].lat, locationResponse[0].lon)
    }
}
