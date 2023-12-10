package com.example.FocusApp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.FocusApp.MyApp
import com.example.FocusApp.model.ProfileData
import com.example.FocusApp.model.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/* ViewModel Factory that will create our view model by injecting the
 ProfileDataStore from the module.
*/
class ProfileFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProfileViewModel(MyApp.appModule.profileRepository) as T
    }
}


/** Simple view model that keeps track of a single value (count in this case) */
class ProfileViewModel(private val profileRepository: ProfileRepository) :
    ViewModel() {
    // private UI state (MutableStateFlow)
    private val _uiState = MutableStateFlow(ProfileData())
    // public getter for the state (StateFlow)
    val uiState: StateFlow<ProfileData> = _uiState.asStateFlow()

    /* Method called when ViewModel is first created */
    init {
        // Start collecting the data from the data store when the ViewModel is created.
        viewModelScope.launch {
            profileRepository.getProfile().collect { profileData ->
                _uiState.value = profileData
            }
        }
    }

    fun setProfileData(newName: String, newLastName: String, newEmail: String, newAge: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(name = newName, lastName = newLastName, email = newEmail, age=newAge) }
            profileRepository.saveProfile (_uiState.value)
        }
    }

    /* Increments the value of the counter stored in the state flow */
    fun increment() {
        viewModelScope.launch {
            var age = _uiState.value.age;
            _uiState.update { currentState ->
                currentState.copy(age = age + 1)
            }
            profileRepository.saveProfile (_uiState.value)
        }
    }


}