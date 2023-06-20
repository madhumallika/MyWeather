package com.madhu.myweather.di

import com.madhu.myweather.data.repository.GetLocationInfoRepository
import com.madhu.myweather.data.repository.GetWeatherInfoRepository
import com.madhu.myweather.data.response.database.WeatherDao
import com.madhu.myweather.useCases.GetLastSavedLocationUseCase
import com.madhu.myweather.useCases.GetLocatioInfoUseCase
import com.madhu.myweather.useCases.GetWeatherInfoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UseCasesModule {

    @Provides
    fun provideGetWeatherInfoUseCase(
        getWeatherInfoRepository: GetWeatherInfoRepository,
        weatherDao: WeatherDao
    ): GetWeatherInfoUseCase {
        return GetWeatherInfoUseCase(getWeatherInfoRepository, weatherDao)
    }

    @Provides
    fun provideGetLocationInfoUseCase(
        getLocationInfoRepository: GetLocationInfoRepository
    ): GetLocatioInfoUseCase {
        return GetLocatioInfoUseCase(getLocationInfoRepository)
    }

    @Provides
    fun provideGetLastSavedLocationUseCase(weatherDao: WeatherDao): GetLastSavedLocationUseCase {
        return GetLastSavedLocationUseCase((weatherDao))
    }
}