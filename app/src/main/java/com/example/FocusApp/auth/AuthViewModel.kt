package com.example.FocusApp.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepository: AuthRepository): ViewModel(){

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