package com.example.FocusApp

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel

/**
 * A ViewModel class that stores the title and author name in the input form along with the list of songs already inputted.
 */
class SongListViewModel : ViewModel() {
    val firstName = mutableStateOf(TextFieldValue())
    val lastName = mutableStateOf(TextFieldValue())
    var age = mutableStateOf(TextFieldValue())
    val email = mutableStateOf(TextFieldValue())
    val title = mutableStateOf(TextFieldValue())
    val author = mutableStateOf(TextFieldValue())
    val songList = mutableStateListOf<Pair<String, String>>()
}