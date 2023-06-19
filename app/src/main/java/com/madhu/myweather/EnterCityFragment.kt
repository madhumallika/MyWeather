package com.madhu.myweather

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.madhu.myweather.databinding.FragmentEnterCityBinding
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EnterCityFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class EnterCityFragment : Fragment() {

    lateinit var binding: FragmentEnterCityBinding
    private val enterCityViewModel: EnterCityViewModel by viewModels()

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

    private fun initSearchButton() {
        binding.btnSearch.setOnClickListener {
            enterCityViewModel.fetchLocationInfo(binding.editTextCity.text.toString())
                .observe(viewLifecycleOwner) {
                    it?.let {
                        binding.editTextCity.error = null
                        findNavController().navigate(
                            EnterCityFragmentDirections.actionEnterCityFragmentToWeatherInfoFragment(
                                it.latitude.toFloat(), it.longitude.toFloat()
                            )
                        )
                    }
                    if (it == null) binding.editTextCity.error = "Enter a Valid City!!"
                }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EnterCityFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EnterCityFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}