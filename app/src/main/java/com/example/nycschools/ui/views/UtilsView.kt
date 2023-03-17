package com.example.nycschools.ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nycschools.model.SchoolsResponseItem

/**
 * Composable function LoadingScreen
 * Creates a progress indicator
 */
@Composable
fun LoadingScreen() {
    Column {
        CircularProgressIndicator(
            modifier = Modifier
                .padding(20.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}

/**
 * Composable function ShowErrorDialog
 * @param e
 * @param retry
 * Creates an AlertDialog when an error occurs
 */
@Composable
fun ShowErrorDialog(
    e: Exception,
    retry: () -> Unit
) {
    val openDialog = remember { mutableStateOf(true) }
    if(openDialog.value){
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            title = { Text(text = "Oops! Something went wrong") },
            text = { Text(text = e.localizedMessage?: "Unexpected error") },
            dismissButton = {
                Button(onClick = {openDialog.value = false}) {
                    Text(text = "Dismiss")
                }
            },
            confirmButton = {
                Button(onClick = { retry() }) {
                    Text(text = "Retry")
                }
            }
        )
    }
}