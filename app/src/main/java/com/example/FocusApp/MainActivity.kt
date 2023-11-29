@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.FocusApp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.FocusApp.screens.LandingScreen
import com.example.FocusApp.screens.SongListApp
import com.example.FocusApp.screens.StatsScreen
import com.example.FocusApp.screens.TaskGeneratorScreen
import com.example.FocusApp.ui.theme.FocusAppTheme
import com.example.FocusApp.ui.theme.TaskListViewModel

/**
 * Main Activity containing a favorite song list application.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this).get(TaskListViewModel::class.java)
        setContent {
            FocusAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.inversePrimary
                ) {
                    // SongListApp(viewModel)
                    // Display landing screen then focus app
                    var showLandingScreen by remember { mutableStateOf(true) }

                    if(showLandingScreen){
                        LandingScreen(onTimeout = { showLandingScreen = false})
                    }
                    else {
                        FocusApp(viewModel)
                    }
                }
            }
        }
    }
}

/**
 * Navigation inspired by the Navigation codelab
 * https://developer.android.com/codelabs/jetpack-compose-navigation
 */
//@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FocusApp(viewModel: TaskListViewModel) {

    // Navigation Setup
    // ----------------
    val navController = rememberNavController()

    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination

    // Find last screen, if not default to Tasks Screen (Song list app at the moment)
    var currentScreen = focusTabRowScreens.find { it.route == currentDestination?.route} ?: Tasks

    // Application Setup
    // ------------------
    Scaffold (
        topBar = {
                FocusTabRow(allScreens = focusTabRowScreens,
                    onTabSelected = { newScreen -> navController.navigateSingleTopTo(newScreen.route)} ,
                    currentScreen = currentScreen
                )
        }
    )
    { innerPadding ->
        FocusNavHost(
            navController = navController,
            viewModel = viewModel,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

/**
 * Extraction of the navhost logic into its own composable
 */
@Composable
fun FocusNavHost(
    navController: NavHostController,
    viewModel: TaskListViewModel, // pass in viewmodel again
    modifier: Modifier
){
    // Navigation controlled through NavHost
    NavHost(
        navController = navController,
        startDestination = Tasks.route,
        modifier = modifier
    ) {

        // Using destination classes to define route and screen
        // See destinations for which screen is passed in
        composable(route = Stats.route){
            StatsScreen()
        }
        composable(route = Tasks.route) {
            SongListApp(viewModel = viewModel)
        }

        composable(route = Generators.route){
            TaskGeneratorScreen()
        }
    }
}


/** Extension method
 * prevents reloading of a tab that is already open
 * restores state if needed
 * allows to pop back to start destination if configured
 */
fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }

