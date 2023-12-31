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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.FocusApp.auth.ResultAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthLoginScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel(factory= AuthViewModelFactory())
) {
    // Viewmodel usages inspired by Talibs slides and KotlinWithCompose Repository
    // View Model Variables
    val userState = authViewModel.currentUser().collectAsState()

    // Auth Results variables
    val signUpResult by authViewModel.signUpResult.collectAsState(ResultAuth.Inactive)
    val signInResult by authViewModel.signInResult.collectAsState(ResultAuth.Inactive)
    val signOutResult by authViewModel.signOutResult.collectAsState(ResultAuth.Inactive)
    val deleteAccountResult by authViewModel.deleteAccountResult.collectAsState(ResultAuth.Inactive)

    val snackbarHostState = remember { SnackbarHostState() } // Material 3 approach
    
    // Show a Snackbar when sign-up is successful, etc.
    LaunchedEffect(signUpResult) {
        signUpResult?.let {
            if (it is ResultAuth.Inactive) {
                return@LaunchedEffect
            }
            if (it is ResultAuth.InProgress) {
                snackbarHostState.showSnackbar("Sign-up In Progress")
                return@LaunchedEffect
            }
            if (it is ResultAuth.Success && it.data) {
                snackbarHostState.showSnackbar("Sign-up Successful")
            } else if (it is ResultAuth.Failure || it is ResultAuth.Success) { // success(false) case
                snackbarHostState.showSnackbar("Sign-up Unsuccessful")
            }
        }
    }

    // Show a Snackbar when sign-in is successful
    LaunchedEffect(signInResult) {
        signInResult?.let {
            if (it is ResultAuth.Inactive) {
                return@LaunchedEffect
            }
            if (it is ResultAuth.InProgress) {
                snackbarHostState.showSnackbar("Sign-in In Progress")
                return@LaunchedEffect
            }
            if (it is ResultAuth.Success && it.data) {
                snackbarHostState.showSnackbar("Sign-in Successful")
            } else if (it is ResultAuth.Failure || it is ResultAuth.Success) { // success(false) case) {
                snackbarHostState.showSnackbar("Sign-in Unsuccessful")
            }
        }
    }

    // Show a Snackbar when sign-out is successful
    LaunchedEffect(signOutResult) {
        signOutResult?.let {
            if (it is ResultAuth.Inactive) {
                return@LaunchedEffect
            }
            if (it is ResultAuth.InProgress) {
                snackbarHostState.showSnackbar("Sign-out In Progress")
                return@LaunchedEffect
            }
            if (it is ResultAuth.Success && it.data) {
                snackbarHostState.showSnackbar("Sign-out Successful")
            } else if (it is ResultAuth.Failure || it is ResultAuth.Success) { // success(false) case) {
                snackbarHostState.showSnackbar("Sign-out Unsuccessful")
            }
        }
    }


    // Show a Snackbar when account deletion is successful
    LaunchedEffect(deleteAccountResult) {
        deleteAccountResult?.let {
            if (it is ResultAuth.Inactive) {
                return@LaunchedEffect
            }
            if (it is ResultAuth.InProgress) {
                snackbarHostState.showSnackbar("Deletion In Progress")
                return@LaunchedEffect
            }
            if (it is ResultAuth.Success && it.data) {
                snackbarHostState.showSnackbar("Account Deleted")
            } else if (it is ResultAuth.Failure || it is ResultAuth.Success) { // success(false) case)  {
                snackbarHostState.showSnackbar("Deletion failed")
            }
        }
    }

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
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.padding(16.dp)
            )
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
