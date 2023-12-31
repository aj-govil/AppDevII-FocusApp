package com.example.FocusApp.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import com.example.FocusApp.data.Task


class TaskListViewModel : ViewModel() {
    // Data Fields for a Task
    val title = mutableStateOf(TextFieldValue())
    val description = mutableStateOf(TextFieldValue())
    val dueTime = mutableStateOf(TextFieldValue())
    val isComplete = mutableStateOf(false)
    var taskList = mutableStateListOf<Task>()
    var deletedTasks = mutableStateListOf<Task>()
}
