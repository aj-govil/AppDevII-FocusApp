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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.FocusApp.auth.AuthViewModel
import com.example.FocusApp.auth.AuthViewModelFactory
import com.example.FocusApp.screens.AuthLoginScreen
import com.example.FocusApp.screens.LandingScreen
import com.example.FocusApp.screens.CreateTaskScreen
import com.example.FocusApp.screens.StatsScreen
import com.example.FocusApp.screens.ViewTasksScreen
import com.example.FocusApp.ui.theme.FocusAppTheme
import com.google.firebase.FirebaseApp
import com.example.FocusApp.screens.StatsScreen
import com.example.FocusApp.ui.theme.FocusAppTheme
import com.example.FocusApp.viewmodels.AccountInformationViewModel
import com.example.FocusApp.viewmodels.TaskListViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.tasks.Task

/**
 * Main Activity containing a favorite song list application.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val taskListViewModel = ViewModelProvider(this).get(TaskListViewModel::class.java)
        val accountInformationViewModel = ViewModelProvider(this).get(AccountInformationViewModel::class.java)
//        lateinit var auth: FirebaseAuth
//
//        auth = Firebase.auth
        FirebaseApp.initializeApp(this)

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
                        FocusApp(
                            taskListViewModel,
                            accountInformationViewModel)
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
fun FocusApp(
    taskListViewModel: TaskListViewModel = TaskListViewModel(),
    accountInformationViewModel: AccountInformationViewModel = AccountInformationViewModel(),
    authViewModel: AuthViewModel = viewModel(factory= AuthViewModelFactory())

    ) {
    val userState = authViewModel.currentUser().collectAsState() // check to see if user loggedin is saved here

    var startDestination = Login.route // Default start is login screen
    var hideTabs = true; // Default gate access to Tabs

    // If user is already logged in, set it to My Tasks
    if (userState.value != null){
        startDestination = Tasks.route
        hideTabs = false
    }

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
                if(!hideTabs){
                    FocusTabRow(allScreens = focusTabRowScreens,
                        onTabSelected = { newScreen -> navController.navigateSingleTopTo(newScreen.route)} ,
                        currentScreen = currentScreen
                    )
                }
            }

    )
    { innerPadding ->
        FocusNavHost(
            navController = navController,
            taskListViewModel = taskListViewModel,
            accountInformationViewModel = accountInformationViewModel,
            authViewModel = authViewModel,
            startDestination = startDestination,
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
    taskListViewModel: TaskListViewModel, // pass in viewmodel again
    accountInformationViewModel: AccountInformationViewModel,
    authViewModel: AuthViewModel = viewModel(factory= AuthViewModelFactory()),
    startDestination: String = Login.route,
    modifier: Modifier
){
    // Navigation controlled through NavHost
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        // Using destination classes to define route and screen
        // See destinations for which screen is passed in
        composable(route = Stats.route){
            StatsScreen()
        }
        composable(route = Tasks.route) {
            ViewTasksScreen(
                taskListViewModel = taskListViewModel,
                accountInformationViewModel = accountInformationViewModel,
                navController = navController
            )
        }

        composable(route = Generators.route){
            CreateTaskScreen(
                taskListViewModel = taskListViewModel,
                navController = navController)
        }

        composable(route = Login.route){
            AuthLoginScreen(
                navController = navController,
                authViewModel = authViewModel)
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

