package com.madhu.myweather.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madhu.myweather.useCases.GetLastSavedLocationUseCase
import com.madhu.myweather.useCases.GetWeatherInfoUseCase
import com.madhu.myweather.useCases.data.WeatherInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WeatherInfoViewModel @Inject constructor(
    private val getWeatherInfoUseCase: GetWeatherInfoUseCase,
    private val getLastSavedLocationUseCase: GetLastSavedLocationUseCase
) : ViewModel() {

    val weatherInfoMutableLiveData: MutableLiveData<WeatherInfo> = MutableLiveData()
    val errorLiveData: MutableLiveData<String> = MutableLiveData()
    val progressBarVisibilityLiveData: MutableLiveData<Boolean> = MutableLiveData()
    fun fetchWeatherInfo(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            progressBarVisibilityLiveData.value = true
            withContext(Dispatchers.IO) {
                try {
                    val weatherInfo = getWeatherInfoUseCase.getWeatherInfo(latitude, longitude)
                    weatherInfoMutableLiveData.postValue(weatherInfo)
                } catch (ex: Exception) {
                    errorLiveData.postValue("Error Occurred when fetching weather Info!!")
                } finally {
                    progressBarVisibilityLiveData.postValue(false)
                }
            }
        }
    }

    fun fetchLastSavedWeatherInfo(): LiveData<WeatherInfo> {
        val mutableLiveData = MutableLiveData<WeatherInfo>()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val lastSavedWeather = getLastSavedLocationUseCase.getLastSavedLocationWeather()
                if (lastSavedWeather == null) errorLiveData.postValue("No saved weather info!!")
                lastSavedWeather?.let {
                    mutableLiveData.postValue(it)
                }
                progressBarVisibilityLiveData.postValue(false)
            }
        }
        return mutableLiveData
    }
}