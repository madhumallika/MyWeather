package com.madhu.myweather.di

import com.madhu.myweather.network.GetLocationService
import com.madhu.myweather.network.GetWeatherInfoService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {
    @Provides
    @Singleton
    @WeatherRetrofit
    fun provideWeatherRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @LocationRetrofit
    fun provideLocationRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/geo/1.0/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideGetWeatherInfoService(@WeatherRetrofit retrofit: Retrofit): GetWeatherInfoService {
        return retrofit.create(GetWeatherInfoService::class.java)
    }

    @Provides
    @Singleton
    fun provideGetLocationService(@LocationRetrofit retrofit: Retrofit): GetLocationService {
        return retrofit.create()
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class WeatherRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LocationRetrofit
