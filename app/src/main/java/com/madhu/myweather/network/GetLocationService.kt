package com.madhu.myweather.network

import API_KEY
import com.madhu.myweather.data.response.locationdata.LocationResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GetLocationService {

    @GET("direct")
    suspend fun getLocation(
        @Query("q") city: String,
        @Query("appid") apiKey: String = API_KEY
    ): LocationResponse
}