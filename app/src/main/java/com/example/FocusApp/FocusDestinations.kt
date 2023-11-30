package com.example.FocusApp

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
object Login: FocusDestination {
    override val route = "login"

}


// Screens to be displayed in the top RallyTabRow
val focusTabRowScreens = listOf(Tasks, Stats, Generators, Login)
