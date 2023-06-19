package com.madhu.myweather.useCases

import com.madhu.myweather.useCases.data.WeatherInfo
import com.madhu.myweather.useCases.repository.GetWeatherInfoRepository

class GetWeatherInfoUseCase(private val getWeatherInforepository: GetWeatherInfoRepository) {

    suspend fun getWeatherInfo(latitude: Double, longitude: Double): WeatherInfo {
        val weatherResponse = getWeatherInforepository.getCurrentWeather(latitude, longitude)
        return WeatherInfo(
            temperature = weatherResponse.main.temp,
            weatherDescription = weatherResponse.weather[0].description,
            highTemperature = weatherResponse.main.temp_max,
            lowTemperature = weatherResponse.main.temp_min,
            humidity = weatherResponse.main.humidity,
            name = weatherResponse.name,
            icon = String.format(IMAGE_BASE_URL, weatherResponse.weather[0].icon)
        )
    }

    companion object {
        const val IMAGE_BASE_URL = "https://openweathermap.org/img/wn/%s@4x.png"
    }
}