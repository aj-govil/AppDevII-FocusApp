@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.ajeshgovil_assignment2

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ajeshgovil_assignment2.ui.theme.AjeshGovil_Assignment2Theme

/**
 * Main Activity containing a favorite song list application.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this).get(SongListViewModel::class.java)
        setContent {
            AjeshGovil_Assignment2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.inversePrimary
                ) {
                    // SongListApp(viewModel)
                    FocusApp(viewModel)
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
fun FocusApp(viewModel: SongListViewModel) {

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

        // Navigation controlled through NavHost
        NavHost(
            navController = navController,
            startDestination = Tasks.route,
            Modifier.padding(innerPadding)
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

