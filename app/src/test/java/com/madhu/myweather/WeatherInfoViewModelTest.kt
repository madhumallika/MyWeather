package com.madhu.myweather

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.madhu.myweather.useCases.GetWeatherInfoUseCase
import com.madhu.myweather.useCases.data.WeatherInfo
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

    lateinit var weatherInfoViewModel: WeatherInfoViewModel
    private val testDispatcher = TestCoroutineDispatcher()

    @MockK
    lateinit var getWeatherInfoUseCase: GetWeatherInfoUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        weatherInfoViewModel = WeatherInfoViewModel(getWeatherInfoUseCase)
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
}