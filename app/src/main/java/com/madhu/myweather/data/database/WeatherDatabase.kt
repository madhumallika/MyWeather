package com.madhu.myweather.data.response.database

import androidx.room.*
import com.madhu.myweather.data.database.WeatherEntity

@Database(entities = [WeatherEntity::class], version = 1)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather")
    suspend fun getWeather(): List<WeatherEntity>

    @Query("DELETE FROM weather")
    suspend fun deleteWeather()

    @Insert
    suspend fun insertWeather(weatherEntity: WeatherEntity)
}
