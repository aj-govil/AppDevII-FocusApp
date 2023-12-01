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
    override val route = "Add Task"
}

object Generators: FocusDestination {
    override val route = "My Tasks"

}
object Login: FocusDestination {
    override val route = "login"

}


// Screens to be displayed in the top RallyTabRow
val focusTabRowScreens = listOf(Generators,Stats, Login)
