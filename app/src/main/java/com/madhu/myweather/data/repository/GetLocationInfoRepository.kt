package com.madhu.myweather.data.repository

import com.madhu.myweather.data.response.locationdata.LocationResponse
import com.madhu.myweather.network.GetLocationService

class GetLocationInfoRepository(
    private val getLocationService: GetLocationService
) {
    suspend fun getLocationInfo(cityName: String): LocationResponse {
        return getLocationService.getLocation(cityName)
    }
}