package com.example.nycschools.rest

import com.example.nycschools.model.SatResponseItem
import com.example.nycschools.model.SchoolsResponseItem
import com.example.nycschools.utils.SchoolState
import com.example.restconnection.services.ServiceCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Interface NetworkRepository
 * @author Sara Iza
 * Defines the methods to call the Service API and get the response
 */

interface NetworkRepository {

    /**
     * Function getAllSchools
     * @return Flow<SchoolState<List<SchoolsResponseItem>>>
     */
    fun getAllSchools(): Flow<SchoolState<List<SchoolsResponseItem>>>

    /**
     * Function getAllSATResults
     * @return Flow<SchoolState<List<SatResponseItem>>>
     */
    fun getAllSatResults(): Flow<SchoolState<List<SatResponseItem>>>

}

class NetworkRepositoryImpl @Inject constructor(
    private val nycSchoolsApi: NycSchoolsApi,
    private val serviceCall: ServiceCall,
    private val ioDispatcher: CoroutineDispatcher
): NetworkRepository{

    override fun getAllSchools(): Flow<SchoolState<List<SchoolsResponseItem>>> = flow{
        emit(SchoolState.LOADING)
        serviceCall.serviceCallApi.restServiceCall(
            {nycSchoolsApi.getSchools()},
            {emit(SchoolState.SUCCESS(it))},
            {emit(SchoolState.ERROR(it))}
        )
    }.flowOn(ioDispatcher)

    override fun getAllSatResults(): Flow<SchoolState<List<SatResponseItem>>> = flow<SchoolState<List<SatResponseItem>>> {
        emit(SchoolState.LOADING)
        serviceCall.serviceCallApi.restServiceCall(
            {nycSchoolsApi.getSat()},
            {emit(SchoolState.SUCCESS(it))},
            {emit(SchoolState.ERROR(it))}
        )
    }.flowOn(ioDispatcher)


}