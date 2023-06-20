package com.madhu.myweather

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.madhu.myweather.useCases.GetLocatioInfoUseCase
import com.madhu.myweather.useCases.data.LocationInfo
import com.madhu.myweather.viewModel.EnterCityViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test


internal class EnterCityViewModelTest {

    @get: Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    lateinit var enterCityViewModel: EnterCityViewModel
    private val testdispatcher = TestCoroutineDispatcher()

    @MockK
    lateinit var getLocationInfoUseCase: GetLocatioInfoUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        enterCityViewModel = EnterCityViewModel(getLocationInfoUseCase)
        Dispatchers.setMain(testdispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testdispatcher.cleanupTestCoroutines()
    }

    @Test
    fun verifyFetchLocationInfoWithSuccessfulResponse() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)
        val locationInfo = LocationInfo(40.5, 67.8)
        coEvery { getLocationInfoUseCase.getLocationInfo("somecity") } returns locationInfo
        enterCityViewModel.fetchLocationInfo("somecity")
        assertEquals(enterCityViewModel.locationInfoMutableLiveData.getOrAwaitValue(), locationInfo)
    }

    @Test
    fun verifyFetchLocationInfoWithFailureResponse() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)
        coEvery { enterCityViewModel.fetchLocationInfo("") } throws Exception()
        enterCityViewModel.fetchLocationInfo("")
        assertNotNull(enterCityViewModel.errorLiveData.getOrAwaitValue())
    }
}