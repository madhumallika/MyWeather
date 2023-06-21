package com.madhu.myweather

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.madhu.myweather.useCases.GetLastSavedLocationUseCase
import com.madhu.myweather.useCases.GetWeatherInfoUseCase
import com.madhu.myweather.useCases.data.WeatherInfo
import com.madhu.myweather.viewModel.WeatherInfoViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class WeatherInfoViewModelTest {

    @get: Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var weatherInfoViewModel: WeatherInfoViewModel
    private val testDispatcher = TestCoroutineDispatcher()

    @MockK
    lateinit var getWeatherInfoUseCase: GetWeatherInfoUseCase

    @MockK
    lateinit var getLastSavedLocationUseCase: GetLastSavedLocationUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        weatherInfoViewModel =
            WeatherInfoViewModel(getWeatherInfoUseCase, getLastSavedLocationUseCase)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun verifyFetchWeatherInfoWithSuccesfulResponse() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)
        val weatherInfo = WeatherInfo(
            name = "plainfield",
            temperature = 20.0,
            weatherDescription = "description",
            highTemperature = 100.0,
            lowTemperature = 10.0,
            humidity = 30,
            icon = "102"
        )
        coEvery { getWeatherInfoUseCase.getWeatherInfo(0.0, 0.0) } returns weatherInfo
        weatherInfoViewModel.fetchWeatherInfo(0.0, 0.0)
        assertEquals(weatherInfoViewModel.weatherInfoMutableLiveData.getOrAwaitValue(), weatherInfo)
    }

    @Test
    fun verifyWeatherInfoWithFailureResponse() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)
        coEvery { getWeatherInfoUseCase.getWeatherInfo(0.0, 0.0) } throws Exception()
        weatherInfoViewModel.fetchWeatherInfo(0.0, 0.0)
        assertNotNull(weatherInfoViewModel.errorLiveData.getOrAwaitValue())
    }

    @Test
    fun verifyLastSavedWeatherInfo() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)
        val lastSavedWeather = WeatherInfo(
            name = "plainfield",
            temperature = 20.0,
            weatherDescription = "description",
            highTemperature = 100.0,
            lowTemperature = 10.0,
            humidity = 30,
            icon = "102"
        )
        coEvery { getLastSavedLocationUseCase.getLastSavedLocationWeather() } returns lastSavedWeather
        weatherInfoViewModel.fetchLastSavedWeatherInfo()
        assertEquals(
            weatherInfoViewModel.fetchLastSavedWeatherInfo().getOrAwaitValue(),
            lastSavedWeather
        )
    }
}