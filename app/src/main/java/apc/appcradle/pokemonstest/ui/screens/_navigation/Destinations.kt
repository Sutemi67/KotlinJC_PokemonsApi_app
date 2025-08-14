package apc.appcradle.pokemonstest.ui.screens._navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController

enum class Destinations(
    val label: String,
    val icon: ImageVector,
    val route: String,
    val navigateOnClick: (NavController) -> Unit,
) {
    MainScreen(
        route = "main_screen",
        label = "Main",
        icon = Icons.Default.Home,
        navigateOnClick = { it.toMainScreen() }
    )
}
