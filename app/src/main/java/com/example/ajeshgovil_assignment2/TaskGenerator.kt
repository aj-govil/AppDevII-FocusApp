package com.example.ajeshgovil_assignment2

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TaskGenerator(){
    Text(
        text = "MY FAVORITE TASKS CREATOR GENERATOOOOOOR",
        style = MaterialTheme.typography.headlineMedium,
        color = Color.Blue,
        modifier = Modifier.padding(8.dp)
    )
    Icon(
        imageVector = Icons.Default.List,
        contentDescription = null,
        tint = Color.Blue,
        modifier = Modifier.size(50.dp)
    )
}