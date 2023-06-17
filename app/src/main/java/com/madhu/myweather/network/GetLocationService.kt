package com.madhu.myweather.network

import KEY
import com.madhu.myweather.data.locationdata.LocationResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GetLocationService {

    @GET("direct")
    suspend fun getLocation(
        @Query("q") city: String,
        @Query("appid") apiKey: String = KEY
    ): LocationResponse
}