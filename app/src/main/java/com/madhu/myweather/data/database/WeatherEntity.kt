package com.madhu.myweather.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Weather")
data class WeatherEntity(
    @PrimaryKey val name: String,
    val temperature: Double,
    val weatherDescription: String,
    val highTemperature: Double,
    val lowTemperature: Double,
    val humidity: Int,
    val icon: String
)