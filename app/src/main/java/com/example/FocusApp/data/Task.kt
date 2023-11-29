package com.example.FocusApp.data

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel


data class Task(
    var title: String,
    var description: String,
    var dueTime: String,
    var isComplete: Boolean
)