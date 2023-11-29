package com.example.FocusApp.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Screen used to display statistics gathered from previous tasks
 */
@Composable
fun StatsScreen(): Unit {
    Text(
        text = "MY FAVORITE STATISTICS SCREEN!!!!!",
        style = MaterialTheme.typography.headlineMedium,
        color = Color.Blue,
        modifier = Modifier.padding(8.dp)
    )
}