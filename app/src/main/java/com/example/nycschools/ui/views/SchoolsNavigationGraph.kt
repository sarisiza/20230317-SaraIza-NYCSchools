package com.example.nycschools.ui.views

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.nycschools.utils.SchoolState
import com.example.nycschools.viewmodel.NycSchoolsViewModel

/**
 * Composable function SchoolsNavigationGraph
 * @param nycSchoolsViewModel
 * @param navController
 * Sets the navigation graph for the application
 */
@Composable
fun SchoolsNavigationGraph(
    nycSchoolsViewModel: NycSchoolsViewModel,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = "schoolsList"
    ) {
        composable("schoolsList"){ //list of schools
            nycSchoolsViewModel.updateBackTrack(false)
            if(nycSchoolsViewModel.schoolsList.value is SchoolState.LOADING)
                nycSchoolsViewModel.getSchoolsList() //network call
            SchoolsInfoPage(
                nycSchoolsViewModel = nycSchoolsViewModel,
                navController = navController
            )
        }
        composable("schoolDetail"){//details of each school
            nycSchoolsViewModel.updateBackTrack(true)
            if(nycSchoolsViewModel.satList.value is SchoolState.LOADING)
                nycSchoolsViewModel.getSatList() //network call
            SchoolDetailPage(
                nycSchoolsViewModel = nycSchoolsViewModel
            )
        }
    }
}