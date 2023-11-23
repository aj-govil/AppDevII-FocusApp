package com.example.FocusApp.ui.theme

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import java.sql.Time
import java.util.Calendar
import java.util.Date


data class Task(
    val title: String,
    val description: String,
    val dueTime: Date,
    val isComplete: Boolean
)

class TaskListViewModel : ViewModel() {
    // Data Fields for a Task
    val title = mutableStateOf(TextFieldValue())
    val description = mutableStateOf(TextFieldValue())
    val dueTime = mutableStateOf(Date())
    val isComplete = mutableStateOf(false)
    val taskList = mutableStateListOf<Task>()

    // Data Fields for a Account
    val firstName = mutableStateOf(TextFieldValue())
    val lastName = mutableStateOf(TextFieldValue())
    var age = mutableStateOf(TextFieldValue())
    val email = mutableStateOf(TextFieldValue())
}