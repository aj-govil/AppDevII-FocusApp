package com.example.FocusApp

interface FocusDestination {
    val route: String
}


/**
 * Focus app navigation destinations
 */

object Generators: FocusDestination {
    override val route = "Add Tasks"

}
object Stats: FocusDestination {
    override val route = "About Us"
}

object Tasks: FocusDestination {
    override val route = "My Tasks"
}
object Login: FocusDestination {
    override val route = "Account"

}

// Screens to be displayed in the top RallyTabRow
val focusTabRowScreens = listOf(Tasks, Stats, Login)
