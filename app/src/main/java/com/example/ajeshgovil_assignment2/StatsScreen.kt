package com.example.ajeshgovil_assignment2

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun StatsScreen(): Unit {
    Text(
        text = "MY FAVORITE STATISTICS SCREEN!!!!!",
        style = MaterialTheme.typography.headlineMedium,
        color = Color.Blue,
        modifier = Modifier.padding(8.dp)
    )
}