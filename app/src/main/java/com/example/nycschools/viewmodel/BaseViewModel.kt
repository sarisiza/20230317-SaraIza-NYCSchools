package com.example.nycschools.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

/**
 * Open class BaseViewModel
 * @author Sara Iza
 * Extends ViewModel
 * Creates a safeViewModelScope
 */

open class BaseViewModel(
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    protected val safeViewModelScope by lazy {
        viewModelScope + ioDispatcher + SupervisorJob() + CoroutineExceptionHandler{ _, e->
            throw Exception(e.localizedMessage)
        }
    }

}