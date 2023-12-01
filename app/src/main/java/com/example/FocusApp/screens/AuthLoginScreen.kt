package com.example.FocusApp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.FocusApp.FocusDestination
import com.example.FocusApp.Tasks
import com.example.FocusApp.auth.AuthViewModel
import com.example.FocusApp.auth.AuthViewModelFactory
import com.example.FocusApp.navigateSingleTopTo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthLoginScreen(
    currentScreen : FocusDestination,
    navController: NavController,
    authViewModel: AuthViewModel = viewModel(factory= AuthViewModelFactory(),
                        )
) {
    val userState = authViewModel.currentUser().collectAsState()
    //Login Values
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(top = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (userState.value == null) {
                // Title
                Text(text = "Not logged in", modifier = Modifier.padding(bottom = 20.dp), fontWeight = FontWeight.SemiBold )

                // User Input Section
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    // Email input
                    EmailField(email = email, onEmailChange = {email = it} )
                    // Password input
                    PasswordField(password = password, onPasswordChange = {password = it} )
                }

                // SIGN UP BUTTON
                // --------------
                Button(onClick = {
                    if (password.isEmpty() && email.isEmpty()){

                    }else {
                        authViewModel.signUp(email, password)
                    }

                }) {
                    Text("Sign up via email")
                }

                // SIGN IN BUTTON
                // --------------
                Button(onClick = {
                    authViewModel.signIn(email, password)
                }) {
                    Text("Sign in via email")
                }
            } else {
                if (userState.value==null)
                    Text("Please sign in")
                else
                    // POST LOGIN "SCREEN"
                    // -----------------
                    Text("Welcome ${userState.value!!.email}", modifier = Modifier.padding(12.dp))
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

                Button(onClick = {
                    navController.navigate("Tasks")
                },
                    modifier = Modifier.padding(50.dp)){
                    Text("Enter App")
                }
            }
        }
    }

}

fun isLoginValid(
    password: String
){

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailField(
    email: String,
    onEmailChange: (String) -> Unit){
    // This implementation was inspired from FitFolios Login Screen
    OutlinedTextField(
        value = email,
        onValueChange = {
            onEmailChange(it)
        },
        label = { Text("Email") },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        )
    )

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordField(
    password: String,
    onPasswordChange: (String) -> Unit){
    // This implementation was inspired from FitFolios Login Screen
    OutlinedTextField(
        value = password,
        onValueChange = {
            onPasswordChange(it)
        },
        label = { Text("Password") },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        )
    )

}
@Composable
fun LoginFields(navController: NavController,
                authViewModel: AuthViewModel,
                ) {


}
@Composable
fun AuthSignUpScreen(
    navController: NavController,
    authViewModel: AuthViewModel,){


}