package com.example.nycschools.ui.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nycschools.model.SatResponseItem
import com.example.nycschools.model.SchoolsResponseItem
import com.example.nycschools.utils.SchoolState
import com.example.nycschools.viewmodel.NycSchoolsViewModel

/**
 * Composable Function SchoolDetailPage
 * Defines the UI for the school details
 */
@Composable
fun SchoolDetailPage(
    nycSchoolsViewModel: NycSchoolsViewModel
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(
                state = rememberScrollState()
            )
            .fillMaxWidth()
    ) {
        val currentSchool = nycSchoolsViewModel.selectedSchool
        currentSchool?.let {
            ShowSchoolDetails(currentSchools = it)
        }
        var currentSat: SatResponseItem? = null
        when(val state = nycSchoolsViewModel.satList.value){
            is SchoolState.ERROR -> {
                ShowErrorDialog(e = state.e) {
                    nycSchoolsViewModel.getSatList()
                }
            }
            SchoolState.LOADING -> {
                LoadingScreen()
            }
            is SchoolState.SUCCESS -> {
                val satResults = state.response
                currentSchool?.let {school ->
                    currentSat = satResults.find {sat ->
                        school.dbn.equals(sat.dbn)
                    }
                }
                currentSat?.let {
                    ShowSatResults(currentSat = it)
                }
            }
        }
    }
}

/**
 * Composable function ShowSchoolDetails
 * @param currentSchools
 * Defines the UI for the School Details
 */
@Composable
fun ShowSchoolDetails(
    currentSchools: SchoolsResponseItem
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        currentSchools.schoolName?.let {
            Text(
                text = it,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
        }
        Spacer(modifier = Modifier.padding(10.dp))
        currentSchools.overviewParagraph?.let {
            CustomText(text = "Description", true)
            CustomText(text = it)
        }
        Spacer(modifier = Modifier.padding(10.dp))
        currentSchools.location?.let {
            CustomText(text = "Address", true)
            CustomText(text = it)
        }
        Spacer(modifier = Modifier.padding(10.dp))
        currentSchools.phoneNumber?.let {
            CustomText(text = "Phone Number", true)
            CustomText(text = it)
        }
        Spacer(modifier = Modifier.padding(10.dp))
        currentSchools.schoolEmail?.let {
            CustomText(text = "Email Address", true)
            CustomText(text = it)
        }
        Spacer(modifier = Modifier.padding(10.dp))
        currentSchools.website?.let {
            CustomText(text = "Website", true)
            CustomText(text = it)
        }
        Spacer(modifier = Modifier.padding(10.dp))
    }
}

/**
 * Composable function ShowSatResults
 * @param currentSat
 * Defines the UI for the SAT Results
 */
@Composable
fun ShowSatResults(
    currentSat: SatResponseItem
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "SAT Average Scores:",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(5.dp)
        )
        Spacer(modifier = Modifier.padding(5.dp))
        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(5.dp)
        ) {
            currentSat.satMathAvgScore?.let {
                CustomText(text = "Math Score: ", true)
                CustomText(text = it)
            }
        }
        Spacer(modifier = Modifier.padding(5.dp))
        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(5.dp)
        ) {
            currentSat.satWritingAvgScore?.let {
                CustomText(text = "Writing Score: ", true)
                CustomText(text = it)
            }
        }
        Spacer(modifier = Modifier.padding(5.dp))
        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(5.dp)
        ) {
            currentSat.satCriticalReadingAvgScore?.let {
                CustomText(text = "Reading Score: ", true)
                CustomText(text = it)
            }
        }
    }
}

@Composable
fun CustomText(
    text: String,
    isTitle: Boolean = false
) {
   if(isTitle){
       Text(
           text = text,
           fontSize = 18.sp,
           fontWeight = FontWeight.Bold,
           textAlign = TextAlign.Center,
           modifier = Modifier
               .padding(5.dp)
       )
   }else{
       Text(
           text = text,
           fontSize = 16.sp,
           textAlign = TextAlign.Center,
           modifier = Modifier
               .padding(5.dp)
       )
   }
}