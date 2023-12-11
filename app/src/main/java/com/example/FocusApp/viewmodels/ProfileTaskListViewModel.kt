package com.example.FocusApp.viewmodels

import androidx.lifecycle.ViewModel
import com.example.FocusApp.db.TaskRepository

// Viewmodel that will use taskRepository (which in turn uses Firestore)
class ProfileTaskListViewModel(private val taskRepository: TaskRepository): ViewModel (){

}