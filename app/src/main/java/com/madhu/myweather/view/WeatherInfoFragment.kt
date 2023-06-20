package com.madhu.myweather.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.madhu.myweather.databinding.FragmentWeatherInfoBinding
import com.madhu.myweather.useCases.data.WeatherInfo
import com.madhu.myweather.viewModel.WeatherInfoViewModel
import com.squareup.picasso.Picasso
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
        val args = WeatherInfoFragmentArgs.fromBundle(requireArguments())
        val latitude = args.latitude
        val longitude = args.longitude
        val useLastLocation = args.useLastSavedLocation
        observeErrorLiveData(view)
        weatherInfoViewModel.progressBarVisibilityLiveData.observe(viewLifecycleOwner) {
            binding.progressBarWeatherInfo.isVisible = it
        }
        if (!useLastLocation) weatherInfoViewModel.fetchWeatherInfo(
            latitude.toDouble(),
            longitude.toDouble()
        )
        else weatherInfoViewModel.fetchLastSavedWeatherInfo().observe(viewLifecycleOwner) {
            setWeatherInfo(it)
        }
        weatherInfoViewModel.weatherInfoMutableLiveData.observe(viewLifecycleOwner) {
            setWeatherInfo(it)
        }
    }

    private fun observeErrorLiveData(view: View) {
        weatherInfoViewModel.errorLiveData.observe(viewLifecycleOwner) {
            val errorSnackbar = Snackbar.make(view, it, Snackbar.LENGTH_LONG)
            errorSnackbar.setAction("Close") {
                errorSnackbar.dismiss()
            }
            errorSnackbar.show()
        }
    }

    private fun setWeatherInfo(weatherInfo: WeatherInfo) {
        binding.txtCity.text = weatherInfo.name
        binding.txtTemperature.text = "${weatherInfo.temperature}°F"
        binding.txtDescription.text = weatherInfo.weatherDescription
        binding.txtTempHigh.text = "H:${weatherInfo.highTemperature}°F"
        binding.txtTempLow.text = "L:${weatherInfo.lowTemperature}°F"
        Picasso.get().load(weatherInfo.icon).into(binding.imgWeatherIcon)
        binding.progressBarWeatherInfo.visibility = View.GONE
    }
}