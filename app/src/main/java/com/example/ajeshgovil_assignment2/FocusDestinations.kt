package com.example.ajeshgovil_assignment2

import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.runtime.Composable

interface FocusDestination {
    val route: String
}


/**
 * Focus app navigation destinations
 */

object Stats: FocusDestination {
    override val route = "stats"
}

object Tasks: FocusDestination {
    override val route = "tasks"
}

object Generators: FocusDestination {
    override val route = "generator"

}


// Screens to be displayed in the top RallyTabRow
val focusTabRowScreens = listOf(Tasks, Stats, Generators)
