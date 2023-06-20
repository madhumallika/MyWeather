package com.madhu.myweather.useCases

import com.madhu.myweather.data.response.database.WeatherDao
import com.madhu.myweather.useCases.data.WeatherInfo

class GetLastSavedLocationUseCase(
    private val weatherDao: WeatherDao
) {
    suspend fun getLastSavedLocationWeather(): WeatherInfo? =
        weatherDao.getWeather().firstOrNull()?.let { weatherEntity ->
            WeatherInfo(
                name = weatherEntity.name,
                temperature = weatherEntity.temperature,
                weatherDescription = weatherEntity.weatherDescription,
                highTemperature = weatherEntity.highTemperature,
                lowTemperature = weatherEntity.lowTemperature,
                humidity = weatherEntity.humidity,
                icon = weatherEntity.icon
            )
        }
}