package com.madhu.myweather.di

import com.madhu.myweather.useCases.GetLocatioInfoUseCase
import com.madhu.myweather.useCases.GetWeatherInfoUseCase
import com.madhu.myweather.useCases.repository.GetLocationInfoRepository
import com.madhu.myweather.useCases.repository.GetWeatherInfoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCasesModule {

    @Provides
    @Singleton
    fun provideGetWeatherInfoUseCase(
        getWeatherInfoRepository: GetWeatherInfoRepository
    ): GetWeatherInfoUseCase {
        return GetWeatherInfoUseCase(getWeatherInfoRepository)
    }

    @Provides
    @Singleton
    fun provideGetLocationInfoUseCase(
        getLocationInfoRepository: GetLocationInfoRepository
    ): GetLocatioInfoUseCase {
        return GetLocatioInfoUseCase(getLocationInfoRepository)
    }
}