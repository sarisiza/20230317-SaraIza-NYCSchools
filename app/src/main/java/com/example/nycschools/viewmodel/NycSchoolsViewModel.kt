package com.example.nycschools.viewmodel

import android.database.sqlite.SQLiteOutOfMemoryException
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.nycschools.model.SatResponseItem
import com.example.nycschools.model.SchoolsResponseItem
import com.example.nycschools.rest.NetworkRepository
import com.example.nycschools.utils.SchoolState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Class NycSchoolsViewModel
 * @author Sara Iza
 * @param networkRepository
 * Extends BaseViewModel
 * ViewModel that contains the information for the NYC Schools
 */
@HiltViewModel
class NycSchoolsViewModel @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val ioDispatcher: CoroutineDispatcher
): BaseViewModel(ioDispatcher) {

    private val _schoolsList: MutableState<SchoolState<List<SchoolsResponseItem>>> =
        mutableStateOf(SchoolState.LOADING)
    val schoolsList: State<SchoolState<List<SchoolsResponseItem>>> get() = _schoolsList

    private val _satList: MutableState<SchoolState<List<SatResponseItem>>> =
        mutableStateOf(SchoolState.LOADING)
    val satList: State<SchoolState<List<SatResponseItem>>> get() = _satList

    //check if back track is necessary
    private val _hasBackTrack: MutableState<Boolean> = mutableStateOf(false)
    val hasBackTrack: State<Boolean> get() = _hasBackTrack

    var selectedSchool: SchoolsResponseItem? = null

    /**
     * Function getSchoolsList
     * Gets the list of schools from the repository and saves it in a Mutable State
     */
    fun getSchoolsList(){
        safeViewModelScope.launch {
            networkRepository.getAllSchools().collect{
                _schoolsList.value = it
            }
        }
    }

    /**
     * Function getSatList
     * Gets the list of SAT Results from the repository and saves it in a Mutable State
     */
    fun getSatList(){
        safeViewModelScope.launch {
            networkRepository.getAllSatResults().collect{
                _satList.value = it
            }
        }
    }

    fun updateBackTrack(back: Boolean){
        _hasBackTrack.value = back
    }

}