package com.madhu.myweather.useCases

import com.madhu.myweather.data.WeatherInfo
import com.madhu.myweather.useCases.repository.GetWeatherInfoRepository

class GetWeatherInfoUseCase(getWeatherInforepository: GetWeatherInfoRepository) {

    fun getWeatherInfo(cityName: String): WeatherInfo {
        return WeatherInfo(
            temperature = "80F",
            weatherDescription = "Mostely Sunny",
            highTemperature = "85F",
            lowTemperature = "60F",
            humidity = 35
        )
    }
}