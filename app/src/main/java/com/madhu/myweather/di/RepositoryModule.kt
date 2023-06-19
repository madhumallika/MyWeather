package com.madhu.myweather.di

import com.madhu.myweather.network.GetLocationService
import com.madhu.myweather.network.GetWeatherInfoService
import com.madhu.myweather.useCases.repository.GetLocationInfoRepository
import com.madhu.myweather.useCases.repository.GetWeatherInfoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideGetWeatherInfoRepository(
        getWeatherInfoService: GetWeatherInfoService
    ): GetWeatherInfoRepository {
        return GetWeatherInfoRepository(getWeatherInfoService)
    }

    @Provides
    @Singleton
    fun provideGetLocationInfoRepository(
        getLocationService: GetLocationService
    ): GetLocationInfoRepository {
        return GetLocationInfoRepository(getLocationService)
    }
}