package com.example.FocusApp.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.FocusApp.MyApp
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepository: AuthRepository): ViewModel(){

    private val _signInResult = MutableStateFlow<ResultAuth<Boolean>?>(ResultAuth.Inactive)
    private val _signUpResult = MutableStateFlow<ResultAuth<Boolean>?>(ResultAuth.Inactive)
    private val _signOutResult = MutableStateFlow<ResultAuth<Boolean>?>(ResultAuth.Inactive)
    private val _deleteAccountResult = MutableStateFlow<ResultAuth<Boolean>?>(ResultAuth.Inactive)

    val signInResult: StateFlow<ResultAuth<Boolean>?> = _signInResult
    val signUpResult: StateFlow<ResultAuth<Boolean>?> = _signUpResult
    val signOutResult: StateFlow<ResultAuth<Boolean>?> = _signOutResult
    val deleteAccountResult: StateFlow<ResultAuth<Boolean>?> = _deleteAccountResult

    // Return a StateFlow so that the composable can awlays update
    // based when the value changes
    fun currentUser(): StateFlow<User?>{
        return authRepository.currentUser();
    }

    fun signUp(email: String, password: String){
        viewModelScope.launch {
            authRepository.signUp(email,password);
        }
    }

    fun signIn(email: String, password: String){
        viewModelScope.launch {
            authRepository.signIn(email,password);
        }
    }

    fun signOut(){
        authRepository.signOut();
    }

    fun delete(){
        viewModelScope.launch {
            authRepository.delete();
        }
    }
}

/* ViewModel Factory that will create our view model by injecting the
      authRepository from the module.
 */
class AuthViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AuthViewModel(MyApp.appModule.authRepository) as T
    }
}