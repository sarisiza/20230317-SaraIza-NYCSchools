package com.example.nycschools.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.nycschools.model.SatResponseItem
import com.example.nycschools.model.SchoolsResponseItem
import com.example.nycschools.rest.NetworkRepository
import com.example.nycschools.utils.SchoolState
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Class NycSchoolsViewModelTest
 * @author Sara Iza
 * Defines unit testing for the view model
 */
@OptIn(ExperimentalCoroutinesApi::class)
class NycSchoolsViewModelTest {

    private lateinit var testObject: NycSchoolsViewModel

    private val mockRepository = mockk<NetworkRepository>(relaxed = true)

    private val testDispatcher = UnconfinedTestDispatcher()

    @get:Rule val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        testObject = NycSchoolsViewModel(mockRepository, testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `get schools list when repository returns a list of schools is success`() {
        every { mockRepository.getAllSchools() } returns flowOf(
           SchoolState.SUCCESS(listOf(
               mockk(relaxed = true),
               mockk(relaxed = true),
               mockk(relaxed = true),
               mockk(relaxed = true),
           ))
        )

        val state: MutableList<SchoolState<List<SchoolsResponseItem>>> = mutableListOf()

        state.add(testObject.schoolsList.value)

        testObject.getSchoolsList()

        testDispatcher.scheduler.advanceUntilIdle()

        state.add(testObject.schoolsList.value)


        assert(state[0] is SchoolState.LOADING)
        assert(state[1] is SchoolState.SUCCESS)
        val sList = (state[1] as SchoolState.SUCCESS).response
        assertEquals(4, sList.size)
    }

    @Test
    fun `get schools when repository retrieves an error returns an error state`() {
        every { mockRepository.getAllSchools() } returns flowOf(
            SchoolState.ERROR(Exception("ERROR"))
        )

        testObject.getSchoolsList()

        testDispatcher.scheduler.advanceUntilIdle()

        val state = testObject.schoolsList.value

        assert(state is SchoolState.ERROR)
        assertEquals((state as SchoolState.ERROR).e.message, "ERROR")
    }

    @Test
    fun `get sat list when repository returns a list of sat results is success`(){
        every { mockRepository.getAllSatResults() } returns flowOf(
            SchoolState.SUCCESS(listOf(
                mockk(relaxed = true),
                mockk(relaxed = true),
                mockk(relaxed = true),
                mockk(relaxed = true),
            ))
        )

        testObject.getSatList()

        testDispatcher.scheduler.advanceUntilIdle()

        var state: SchoolState<List<SatResponseItem>> = testObject.satList.value

        assert(state is SchoolState.SUCCESS)
        val sList = (state as SchoolState.SUCCESS).response
        assertEquals(4, sList.size)

    }
}