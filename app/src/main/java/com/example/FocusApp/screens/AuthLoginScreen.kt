package com.example.FocusApp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.FocusApp.Tasks
import com.example.FocusApp.auth.AuthViewModel
import com.example.FocusApp.auth.AuthViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
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
    var confirmPassword by rememberSaveable { mutableStateOf("") } // can be added to signup if diff screen

    // Error message variables
    var showEmailError by remember { mutableStateOf(false) } // flag to display error message
    var showPasswordError by remember { mutableStateOf(false) } // flag to display error message
    var emailErrorMessage by remember { mutableStateOf("Temp Email Error") } // error message itself
    var passwordErrorMessage by remember { mutableStateOf("Temp Password Error") } // error message itself

    //Authentication error variables
    var showAuthError by remember { mutableStateOf(false)} // flag to show if firebase couldn't log in/sign up
    var authErrorMessage by remember { mutableStateOf("Could not authenticate with firebase")}

    // Input Validation Methods
    // ------------------------
    fun isPasswordValid(
        password: String,
    ): Boolean{
        return if (password.length < 6) {
            passwordErrorMessage = "Password must be at least 6 characters"
            showPasswordError = true;
            false
        } else {
            showPasswordError = false;
            true
        }
    }
    fun isEmailValid(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return if (email.matches(emailPattern.toRegex())){
            showEmailError = false;
            emailErrorMessage = "" // set error message
            true
        }else {
            emailErrorMessage = "Invalid Email" // set error message
            showEmailError = true;
            return false;
        }
    }

    // Button onClick functions
    // ------------------------

    // note: this is repeating code however usually signups require more info, this would be able to deal with that
    val signUpClick: () -> Unit = {
        if ( /* !isEmailValid(email) || **/ !isPasswordValid(password)) {
            // Validation failed, do not proceed with sign up
        } else {
            authViewModel.signUp(email, password)
        }
    }

    val signInClick: () -> Unit = {
        if (isEmailValid(email) || isPasswordValid(password)) {
            // Validation failed, do not proceed with sign in
        } else {
            authViewModel.signIn(email, password)
        }
    }

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
                // ==================
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    
                    if (showEmailError) {
                        Text(
                            text = emailErrorMessage,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                                .semantics(mergeDescendants = true) {
                                    contentDescription = "Email Error Message"
                                },
                            textAlign = TextAlign.Center,
                        )
                    }
                    // Email input
                    EmailField(
                        email = email,
                        onEmailChange = {email = it},
                        isError = showEmailError,
                        errorText = emailErrorMessage,)

                    if (showPasswordError) {
                        Text(
                            text = passwordErrorMessage,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp,)
                                .semantics(mergeDescendants = true) {
                                    contentDescription = "Password Error Message"
                                },
                            textAlign = TextAlign.Center,
                        )
                    }
                    // Password input
                    PasswordField(password = password, onPasswordChange = {password = it}, showPasswordError, passwordErrorMessage )
                }

                // SIGN UP BUTTON
                // --------------
                Button(modifier = Modifier
                    .padding(10.dp)
                    .height(48.dp), onClick = {
                    if (!isEmailValid(email) || !isPasswordValid(password)) {
                        // Validation failed, do not proceed with sign up
                    } else {
                        authViewModel.signUp(email, password)
                        // TODO: Display error message is userState value is null (Signup didnt work)
                    }
                }) {
                    Text("Sign up via email")
                }

                // SIGN IN BUTTON
                // --------------
                Button(modifier = Modifier
                    .padding(10.dp)
                    .height(48.dp), onClick = {
                    // Note: This verification isn't checking firebase so results can look wonky -- TODO below should address
                    if(!isEmailValid(email) || !isPasswordValid(password)){
                        // Validation failed, do not proceed with sign up
                    }else {
                        authViewModel.signIn(email, password)
                    }
                    // TODO: Display error message is userState value is null (Signup didnt work)
                    
                }) {
                    Text("Sign in via email")
                }
            } else {
                if (userState.value==null)
                    Text("Please sign in")
                else
                    // POST LOGIN "SCREEN"
                    // -----------------
                    Text("Welcome ${userState.value!!.email}",
                        modifier = Modifier
                            .padding(12.dp)
                            .semantics(mergeDescendants = true) {
                                contentDescription = "Login Screen Header"
                            },)
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
                    navController.navigate(Tasks.route) // TODO: Swap with focus destination to keep access to above?
                                                        // Or setup popBackStack to return to Tasks -> must make this default, would have to add logic to redirect to login
                },
                    modifier = Modifier
                        .padding(50.dp)
                        .semantics(mergeDescendants = true) {
                            contentDescription = "Enter Button"
                        },
                    ){
                    Text("Enter App")
                }
            }
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailField(
    email: String,
    onEmailChange: (String) -> Unit,
    isError: Boolean,
    errorText: String,
){
    // This implementation was inspired from FitFolios Login Screen
    OutlinedTextField(
        value = email,
        onValueChange = {
            onEmailChange(it)
        },
        label = { Text("Email") },
        isError = isError, // Changes box field to red outline and red text
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),

    )

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordField(
    password: String,
    onPasswordChange: (String) -> Unit,
    showPasswordError: Boolean,
    passwordErrorMessage: String
){
    // This implementation was inspired from FitFolios Login Screen
    OutlinedTextField(
        value = password,
        onValueChange = {
            onPasswordChange(it)
            //TODO: Add email validation that affects isError
        },
        label = { Text("Password") },
        isError = false, // Changes box field to red outline and red text
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        )
    )

}
