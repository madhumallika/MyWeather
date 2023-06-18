package com.madhu.myweather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.madhu.myweather.databinding.FragmentWeatherInfoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherInfoFragment : Fragment() {

    private val weatherInfoViewModel: WeatherInfoViewModel by viewModels()
    lateinit var binding: FragmentWeatherInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val cityName = WeatherInfoFragmentArgs.fromBundle(requireArguments()).cityName
        weatherInfoViewModel.fetchWeatherInfo(cityName)
        weatherInfoViewModel.weatherInfoMutableLiveData.observe(viewLifecycleOwner) {
            binding.txtCity.text = it.name
            binding.txtTemperature.text = it.temperature.toString()
            binding.txtDescription.text = it.weatherDescription
            binding.txtTempHigh.text = it.highTemperature.toString()
            binding.txtTempLow.text = it.lowTemperature.toString()
        }
    }
}