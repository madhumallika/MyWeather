package com.madhu.myweather.useCases

import com.madhu.myweather.useCases.data.WeatherInfo
import com.madhu.myweather.useCases.repository.GetWeatherInfoRepository

class GetWeatherInfoUseCase(val getWeatherInforepository: GetWeatherInfoRepository) {

    suspend fun getWeatherInfo(cityName: String): WeatherInfo {
        val weatherResponse = getWeatherInforepository.getCurrentWeather(cityName)
        return WeatherInfo(
            temperature = weatherResponse.main.temp,
            weatherDescription = weatherResponse.weather[0].description,
            highTemperature = weatherResponse.main.temp_max,
            lowTemperature = weatherResponse.main.temp_min,
            humidity = weatherResponse.main.humidity,
            name = weatherResponse.name
        )
    }
}