package com.example.nycschools.utils

/**
 * Sealed class SchoolState
 * @author Sara Iza
 * Defines the states that will be available in the UI
 */

sealed class SchoolState<out T>{

    object LOADING: SchoolState<Nothing>()

    data class SUCCESS<T>(val response: T): SchoolState<T>()

    data class ERROR(val e: Exception): SchoolState<Nothing>()

}
