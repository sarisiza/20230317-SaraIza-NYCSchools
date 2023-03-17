package com.example.nycschools

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nycschools.ui.theme.NYCSchoolsTheme
import com.example.nycschools.ui.views.SchoolsNavigationGraph
import com.example.nycschools.viewmodel.NycSchoolsViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Class MainActivity
 * @author Sara Iza
 * Extends ComponentActivity
 * Main Activity of the application
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    /**
     * Overrides: onCreate
     * Sets the content for the activity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val nycSchoolsViewModel = hiltViewModel<NycSchoolsViewModel>()
            val navController = rememberNavController()
            NYCSchoolsTheme {
                MainPage(
                    nycSchoolsViewModel = nycSchoolsViewModel,
                    navController = navController
                )
            }
        }
    }

}

/**
 * Composable function MainPage
 * @param nycSchoolsViewModel
 * @param navController
 * Sets the Scaffold for the Main Activity
 */
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainPage(
    nycSchoolsViewModel: NycSchoolsViewModel,
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(5.dp),
                title = {
                    Text(
                        text = "NYC Schools",
                        fontSize = 25.sp,
                        modifier = Modifier.padding(10.dp)
                    )
                },
                navigationIcon = {
                    Row {
                        if(nycSchoolsViewModel.hasBackTrack.value){
                            IconButton(
                                onClick = { navController.navigateUp() },
                                modifier = Modifier.padding(10.dp)
                            ) {
                                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                            }
                        }
                    }
                }
            )
        }
    ) {
        SchoolsNavigationGraph(
            nycSchoolsViewModel = nycSchoolsViewModel,
            navController = navController
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NYCSchoolsTheme {

    }
}