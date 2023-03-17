package com.example.nycschools.ui.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.nycschools.R
import com.example.nycschools.model.SchoolsResponseItem
import com.example.nycschools.utils.SchoolState
import com.example.nycschools.viewmodel.NycSchoolsViewModel

/**
 * Composable function SchoolsInfoPage
 * @param nycSchoolsViewModel
 * @param navController
 * Reads the schoolsList state and update the UI accordingly
 */
@Composable
fun SchoolsInfoPage(
    nycSchoolsViewModel: NycSchoolsViewModel,
    navController: NavHostController
) {
    when(val state = nycSchoolsViewModel.schoolsList.value){
        is SchoolState.ERROR -> {
            ShowErrorDialog(e = state.e) {
                nycSchoolsViewModel.getSchoolsList()
            }
        }
        SchoolState.LOADING -> {
            LoadingScreen()
        }
        is SchoolState.SUCCESS -> { //data arrived from API
            val sList = state.response.sortedBy { it.schoolName }
            SchoolsList(
                items = sList
            ){
                nycSchoolsViewModel.selectedSchool = it
                navController.navigate("schoolDetail")
            }
        }
    }
}

/**
 * Composable function SchoolsList
 * @param items
 * @param selectedItem
 * Creates a list of generic items
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SchoolsList(
    items: List<SchoolsResponseItem>,
    selectedItem: (SchoolsResponseItem) -> Unit
) {
    val grouped = items.groupBy { it.schoolName?.get(0) }
    LazyColumn(
        state = rememberLazyListState()
    ){
        //create each item from the list
        grouped.forEach{(initial,schools)->
            initial?.let {
                stickyHeader {
                    Header(initial = it)
                }
            }
            itemsIndexed(items = schools){_,school ->
                ItemHolder(
                    detail = school,
                    selectedItem = selectedItem
                )
            }
        }
    }
}

@Composable
fun Header(
    initial: Char
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.lightBlue))
    ) {
        Text(
            text = initial.toString(),
            color = Color.Blue,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(5.dp)
        )
    }
}

/**
 * Composable function ItemHolder
 * @param detail
 * @param selectedItem
 * Defines the UI structure for each item in the list
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ItemHolder(
    detail: SchoolsResponseItem,
    selectedItem: (SchoolsResponseItem) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth(),
        onClick = {
            selectedItem(detail)
        }
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            detail.schoolName?.let {
                Text(
                    text = it,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

