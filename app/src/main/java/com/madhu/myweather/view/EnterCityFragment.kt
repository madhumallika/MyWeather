package com.madhu.myweather.view

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.madhu.myweather.databinding.FragmentEnterCityBinding
import com.madhu.myweather.viewModel.EnterCityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EnterCityFragment : Fragment() {

    lateinit var binding: FragmentEnterCityBinding
    private val enterCityViewModel: EnterCityViewModel by viewModels()
    private var locationManager: LocationManager? = null
    val sharedPreferences = context?.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
    val editor = sharedPreferences?.edit()

    private val enterCityTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
            val isEditTextEmpty = charSequence.isNullOrEmpty()
            binding.btnSearch.isEnabled = !isEditTextEmpty
        }

        override fun afterTextChanged(p0: Editable?) {
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentEnterCityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.editTextCity.addTextChangedListener(enterCityTextWatcher)
        initSearchButton()
        initCurrentLocationButton()
        initLastSearch()
        enterCityViewModel.progressBarVisibilityLiveData.observe(viewLifecycleOwner) {
            binding.progressBarEnterCity.isVisible = it
        }
        enterCityViewModel.errorLiveData.observe(viewLifecycleOwner) {
            val errorSnackbar = Snackbar.make(view, it, Snackbar.LENGTH_LONG)
            errorSnackbar.setAction("Close") {
                errorSnackbar.dismiss()
            }
            errorSnackbar.show()
        }
    }

    private fun initCurrentLocationButton() {
        binding.btnCurrentLocation.setOnClickListener {
            listenToLocationUpdates()
        }
    }

    private fun listenToLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            locationManager =
                requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
            locationManager?.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 0, 0f, locationListener
            )
            locationManager?.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                0,
                0f,
                locationListener
            )
        } else {
            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }


    private fun initLastSearch() {
        val latitude = sharedPreferences?.getFloat("latitude", 0.0f)
        val longitude = sharedPreferences?.getFloat("longitude", 0.0f)
        binding.btnLastSearch.setOnClickListener {
            findNavController().navigate(
                EnterCityFragmentDirections.actionEnterCityFragmentToWeatherInfoFragment(
                    0f, 0f, true
                )
            )
        }

    }

    private fun initSearchButton() {
        binding.btnSearch.setOnClickListener {
            enterCityViewModel.fetchLocationInfo(binding.editTextCity.text.toString())
                .observe(viewLifecycleOwner) {
                    it?.let {
                        editor?.putFloat("latitude", it.latitude.toFloat())
                        editor?.putFloat("longitude", it.longitude.toFloat())
                        editor?.apply()
                        binding.editTextCity.error = null
                        findNavController().navigate(
                            EnterCityFragmentDirections.actionEnterCityFragmentToWeatherInfoFragment(
                                it.latitude.toFloat(), it.longitude.toFloat(), false
                            )
                        )
                    }
                    if (it == null) binding.editTextCity.error = "Enter a Valid City!!"
                }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            listenToLocationUpdates()
        } else {
            val snackbar = Snackbar.make(
                requireView(),
                "Location Permission is needed to get current location",
                Snackbar.LENGTH_LONG
            )
            snackbar.setAction("Close") {
                snackbar.dismiss()
            }
            snackbar.show()
        }
    }

    private val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            locationManager?.removeUpdates(this)
            findNavController().navigate(
                EnterCityFragmentDirections.actionEnterCityFragmentToWeatherInfoFragment(
                    location.latitude.toFloat(), location.longitude.toFloat(), false
                )
            )
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}

        override fun onProviderEnabled(provider: String) {}

        override fun onProviderDisabled(provider: String) {}
    }

    override fun onStop() {
        super.onStop()
        locationManager?.removeUpdates(locationListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        locationManager?.removeUpdates(locationListener)
    }

    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 1001
    }
}