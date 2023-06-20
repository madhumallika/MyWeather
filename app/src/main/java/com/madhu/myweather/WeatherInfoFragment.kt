package com.madhu.myweather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.madhu.myweather.databinding.FragmentWeatherInfoBinding
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
        val latitude = WeatherInfoFragmentArgs.fromBundle(requireArguments()).latitude
        val longitude = WeatherInfoFragmentArgs.fromBundle(requireArguments()).longitude
        weatherInfoViewModel.errorLiveData.observe(viewLifecycleOwner) {
            val errorSnackbar = Snackbar.make(view, it, Snackbar.LENGTH_LONG)
            errorSnackbar.setAction("Close") {
                errorSnackbar.dismiss()
            }
            errorSnackbar.show()
        }
        weatherInfoViewModel.progressBarVisibilityLiveData.observe(viewLifecycleOwner) {
            binding.progressBarWeatherInfo.isVisible = it
        }
        binding.progressBarWeatherInfo.visibility = View.VISIBLE
        weatherInfoViewModel.fetchWeatherInfo(latitude.toDouble(), longitude.toDouble())
        weatherInfoViewModel.weatherInfoMutableLiveData.observe(viewLifecycleOwner) {
            binding.txtCity.text = it.name
            binding.txtTemperature.text = "${it.temperature}°F"
            binding.txtDescription.text = it.weatherDescription
            binding.txtTempHigh.text = "H:${it.highTemperature}°F"
            binding.txtTempLow.text = "L:${it.lowTemperature}°F"
            Picasso.get().load(it.icon).into(binding.imgWeatherIcon)
            binding.progressBarWeatherInfo.visibility = View.GONE
        }
    }
}