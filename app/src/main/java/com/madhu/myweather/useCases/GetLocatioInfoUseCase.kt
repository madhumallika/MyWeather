package com.madhu.myweather.useCases

import com.madhu.myweather.useCases.data.LocationInfo
import com.madhu.myweather.useCases.repository.GetLocationInfoRepository

class GetLocatioInfoUseCase(
    private val getLocationInfoRepository: GetLocationInfoRepository
) {
    suspend fun getLocationInfo(cityName: String): LocationInfo? {
        val locationResponse = getLocationInfoRepository.getLocationInfo(cityName)
        return locationResponse.firstOrNull()?.let {
            LocationInfo(it.lat, it.lon)
        }
    }
}