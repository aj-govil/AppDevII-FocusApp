package com.example.FocusApp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.FocusApp.auth.AuthViewModel
import com.example.FocusApp.auth.AuthViewModelFactory

@Composable
fun AuthLoginScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel(factory= AuthViewModelFactory(),
                        )
) {
    val userState = authViewModel.currentUser().collectAsState()
    //Login Values
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }

    Column {
        if (userState.value == null) {
            Text("Not logged in")
            Button(onClick = {
                authViewModel.signUp("myname2@name.com", "Abcd1234!")
            }) {
                Text("Sign up via email")
            }
            Button(onClick = {
                authViewModel.signIn("myname2@name.com", "Abcd1234!")
                navController.navigate("Tasks")
            }) {
                Text("Sign in via email")
            }
        } else {
            if (userState.value==null)
                Text("Please sign in")
            else
                Text("Welcome ${userState.value!!.email}")
            Button(onClick = {
                authViewModel.signOut()
            }) {
                Text("Sign out")
            }
            Button(onClick = {
                authViewModel.delete()
            }) {
                Text("Delete account")
            }
        }
    }
}