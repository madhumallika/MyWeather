package com.madhu.myweather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherInfoFragment : Fragment() {

    private val weatherInfoViewModel: WeatherInfoViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val cityName = WeatherInfoFragmentArgs.fromBundle(requireArguments()).cityName
        weatherInfoViewModel.fetchWeatherInfo(cityName)
        weatherInfoViewModel.weatherInfoMutableLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(context, "WeatherInfoFragment : $it", Toast.LENGTH_LONG).show()
        }
    }
}