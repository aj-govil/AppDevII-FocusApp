package com.example.basicstatecodelab

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.basicstatecodelab.StatelessCounter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatefulCounter(addToList: (String) -> Unit) {

    var waterCount by rememberSaveable { mutableStateOf(0) }
    var input by remember { mutableStateOf("") }

    TextField(value = input, onValueChange = { input = it})
    StatelessCounter(waterCount, {
        waterCount++
        addToList(input) // hoist
        input = ""// reset
    })
}
