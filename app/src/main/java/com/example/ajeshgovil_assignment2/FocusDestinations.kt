package com.example.ajeshgovil_assignment2

import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.runtime.Composable

interface FocusDestination {
    val route: String
    val screen: @Composable () -> Unit
}


/**
 * Focus app navigation destinations
 */

object Stats: FocusDestination {
    override val route = "stats"
    override val screen: @Composable () -> Unit = { StatsScreen() }
}

// TODO: ViewModel, not sure if what I wrote works as intended, also name change
object Tasks: FocusDestination {
    override val route = "tasks"
    override val screen: @Composable () -> Unit = { SongListApp(SongListViewModel()) }
}

object Generator: FocusDestination {
    override val route = "generator"
    override val screen: @Composable () -> Unit = { TaskGenerator() }

}