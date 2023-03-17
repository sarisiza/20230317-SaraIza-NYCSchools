package com.example.nycschools.rest

import com.example.nycschools.model.SatResponseItem
import com.example.nycschools.model.SchoolsResponseItem
import com.example.nycschools.utils.SchoolState
import com.example.restconnection.services.ServiceCall
import com.example.restconnection.utils.InternetConnectionException
import com.example.restconnection.utils.NetworkState
import io.mockk.InternalPlatformDsl.toArray
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * Class NetworkRepositoryImplTest
 * @author Sara Iza
 * Test the Network Repository
 */

@OptIn(ExperimentalCoroutinesApi::class)
class NetworkRepositoryImplTest {

    private lateinit var testObject: NetworkRepository

    private val mockApi: NycSchoolsApi = mockk(relaxed = true)
    private lateinit var mockServiceCall: ServiceCall
    private val mockNetworkState: NetworkState = mockk(relaxed = true)

    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mockServiceCall = ServiceCall(mockNetworkState)
        testObject = NetworkRepositoryImpl(
            mockApi,
            mockServiceCall,
            testDispatcher
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `get all schools when api gets a list of schools return a success state`(){

        every { mockNetworkState.isInternetEnabled() } returns true

        coEvery { mockApi.getSchools() } returns mockk{
            every { isSuccessful } returns true
            every { body() } returns listOf(
                mockk(relaxed = true),
                mockk(relaxed = true),
                mockk(relaxed = true)
            )
        }

        val states = mutableListOf<SchoolState<List<SchoolsResponseItem>>>()
        val job = testScope.launch {
            testObject.getAllSchools().collect{
                states.add(it)
            }
        }

        assertEquals(2,states.size)
        assert(states[1] is SchoolState.SUCCESS)
        val success = states[1] as SchoolState.SUCCESS<List<SchoolsResponseItem>>
        assertEquals(3,success.response.size)

        job.cancel()

    }

    @Test
    fun `get all sat results when api gets a list of sat results return a success state`(){

        every { mockNetworkState.isInternetEnabled() } returns true

        coEvery { mockApi.getSat() } returns mockk{
            every { isSuccessful } returns true
            every { body() } returns listOf(
                mockk(relaxed = true),
                mockk(relaxed = true),
                mockk(relaxed = true)
            )
        }

        val states = mutableListOf<SchoolState<List<SatResponseItem>>>()
        val job = testScope.launch {
            testObject.getAllSatResults().collect{
                states.add(it)
            }
        }

        assertEquals(2,states.size)
        assert(states[1] is SchoolState.SUCCESS)
        val success = states[1] as SchoolState.SUCCESS<List<SatResponseItem>>
        assertEquals(3,success.response.size)

        job.cancel()

    }

    @Test
    fun `get all sat results when network is not available return an error state`(){

        every { mockNetworkState.isInternetEnabled() } returns false

        coEvery { mockApi.getSat() } returns mockk{
            every { isSuccessful } returns true
            every { body() } returns listOf(
                mockk(relaxed = true),
                mockk(relaxed = true),
                mockk(relaxed = true)
            )
        }

        val states = mutableListOf<SchoolState<List<SatResponseItem>>>()
        val job = testScope.launch {
            testObject.getAllSatResults().collect{
                states.add(it)
            }
        }

        assertEquals(2,states.size)
        assert(states[1] is SchoolState.ERROR)
        val error = states[1] as SchoolState.ERROR
        assert(error.e is InternetConnectionException)

        job.cancel()

    }

    @Test
    fun `get all schools when network is not available return an error state`(){

        every { mockNetworkState.isInternetEnabled() } returns false

        coEvery { mockApi.getSchools() } returns mockk{
            every { isSuccessful } returns true
            every { body() } returns listOf(
                mockk(relaxed = true),
                mockk(relaxed = true),
                mockk(relaxed = true)
            )
        }

        val states = mutableListOf<SchoolState<List<SchoolsResponseItem>>>()
        val job = testScope.launch {
            testObject.getAllSchools().collect{
                states.add(it)
            }
        }

        assertEquals(2,states.size)
        assert(states[1] is SchoolState.ERROR)
        val error = states[1] as SchoolState.ERROR
        assert(error.e is InternetConnectionException)

        job.cancel()

    }

}