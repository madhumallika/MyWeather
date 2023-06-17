package com.madhu.myweather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madhu.myweather.data.WeatherInfo
import com.madhu.myweather.useCases.GetWeatherInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WeatherInfoViewModel @Inject constructor(
    private val getWeatherInfoUseCase: GetWeatherInfoUseCase
) : ViewModel() {

    val weatherInfoMutableLiveData: MutableLiveData<WeatherInfo> = MutableLiveData()
    fun fetchWeatherInfo(cityName: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val weatherInfo = getWeatherInfoUseCase.getWeatherInfo(cityName)
                weatherInfoMutableLiveData.postValue(weatherInfo)
            }

        }

    }
}