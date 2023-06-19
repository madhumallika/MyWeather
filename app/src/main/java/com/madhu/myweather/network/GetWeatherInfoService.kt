package com.madhu.myweather.network

import API_KEY
import com.madhu.myweather.data.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GetWeatherInfoService {

    @GET("weather")
    suspend fun getWeatherData(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String = API_KEY,
        @Query("units") metric: String = "imperial"
    ): WeatherResponse
}