package com.example.nycschools.rest

import com.example.nycschools.model.SatResponseItem
import com.example.nycschools.model.SchoolsResponseItem
import retrofit2.Response
import retrofit2.http.GET


/**
 * Interface NycSchoolsApi
 * @author Sara Iza
 * Defines the URL and the HTTPS Get methods to reach the API
 */

interface NycSchoolsApi {

    /**
     * Function getSchools
     * GET method to retrieve information from Schools API
     * @return Response<List<SchoolsResponseItem>>
     */
    @GET(SCHOOL_PATH)
    suspend fun getSchools(): Response<List<SchoolsResponseItem>>

    /**
     * Function getSat
     * GET method to retrieve information from SAT API
     * @return Response<List<SatResponseItem>>
     */
    @GET(SAT_PATH)
    suspend fun getSat(): Response<List<SatResponseItem>>

    //Defines the URL and paths for the API
    companion object{
        const val BASE_URL = "https://data.cityofnewyork.us/resource/"
        private const val SCHOOL_PATH = "s3k6-pzi2.json"
        private const val SAT_PATH = "f9bf-2cp4.json"
    }

}