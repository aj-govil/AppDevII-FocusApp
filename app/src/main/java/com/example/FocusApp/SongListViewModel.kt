package com.example.FocusApp

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel

/**
 * A ViewModel class that stores the title and author name in the input form along with the list of songs already inputted.
 */
class SongListViewModel : ViewModel() {
    val title = mutableStateOf(TextFieldValue())
    val author = mutableStateOf(TextFieldValue())
    val songList = mutableStateListOf<Pair<String, String>>()
}