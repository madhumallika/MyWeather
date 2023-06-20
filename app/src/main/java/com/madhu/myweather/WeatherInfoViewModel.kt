package com.madhu.myweather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madhu.myweather.useCases.GetWeatherInfoUseCase
import com.madhu.myweather.useCases.data.WeatherInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherInfoViewModel @Inject constructor(
    private val getWeatherInfoUseCase: GetWeatherInfoUseCase
) : ViewModel() {

    val weatherInfoMutableLiveData: MutableLiveData<WeatherInfo> = MutableLiveData()
    val errorLiveData: MutableLiveData<String> = MutableLiveData()
    val progressBarVisibilityLiveData: MutableLiveData<Boolean> = MutableLiveData()
    fun fetchWeatherInfo(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            progressBarVisibilityLiveData.value = true

            try {
                val weatherInfo = getWeatherInfoUseCase.getWeatherInfo(latitude, longitude)
                weatherInfoMutableLiveData.postValue(weatherInfo)
                progressBarVisibilityLiveData.value = false
            } catch (ex: Exception) {
                errorLiveData.value = "Error Occurred when fetching weather Info!!"
            }
        }

    }
}