package com.madhu.myweather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madhu.myweather.useCases.GetLocatioInfoUseCase
import com.madhu.myweather.useCases.data.LocationInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EnterCityViewModel @Inject constructor(
    private val getLocationInfoUseCase: GetLocatioInfoUseCase
) : ViewModel() {

    val progressBarVisibilityLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val errorLiveData: MutableLiveData<String> = MutableLiveData()

    fun fetchLocationInfo(cityName: String): MutableLiveData<LocationInfo?> {
        val locationInfoMutableLiveData: MutableLiveData<LocationInfo?> = MutableLiveData()
        try {
            viewModelScope.launch {
                progressBarVisibilityLiveData.value = true
                val locationInfo = getLocationInfoUseCase.getLocationInfo(cityName)
                locationInfoMutableLiveData.postValue(locationInfo)
                progressBarVisibilityLiveData.value = false
            }
        } catch (ex: Exception) {
            errorLiveData.value = "An Error occurred while fetching Location"
        }
        return locationInfoMutableLiveData
    }
}