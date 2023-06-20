package com.madhu.myweather.useCases

import com.madhu.myweather.data.database.WeatherEntity
import com.madhu.myweather.data.repository.GetWeatherInfoRepository
import com.madhu.myweather.data.response.database.WeatherDao
import com.madhu.myweather.useCases.data.WeatherInfo

class GetWeatherInfoUseCase(
    private val getWeatherInforepository: GetWeatherInfoRepository,
    private val weatherDao: WeatherDao
) {

    suspend fun getWeatherInfo(latitude: Double, longitude: Double): WeatherInfo {
        val weatherResponse = getWeatherInforepository.getCurrentWeather(latitude, longitude)
        val weatherInfo = WeatherInfo(
            temperature = weatherResponse.main.temp,
            weatherDescription = weatherResponse.weather[0].description,
            highTemperature = weatherResponse.main.temp_max,
            lowTemperature = weatherResponse.main.temp_min,
            humidity = weatherResponse.main.humidity,
            name = weatherResponse.name,
            icon = String.format(IMAGE_BASE_URL, weatherResponse.weather[0].icon)
        )
        weatherDao.deleteWeather()
        weatherDao.insertWeather(
            WeatherEntity(
                name = weatherInfo.name,
                temperature = weatherInfo.temperature,
                weatherDescription = weatherInfo.weatherDescription,
                highTemperature = weatherInfo.highTemperature,
                lowTemperature = weatherInfo.lowTemperature,
                humidity = weatherInfo.humidity,
                icon = weatherInfo.icon
            )
        )
        return weatherInfo
    }

    companion object {
        const val IMAGE_BASE_URL = "https://openweathermap.org/img/wn/%s@4x.png"
    }
}