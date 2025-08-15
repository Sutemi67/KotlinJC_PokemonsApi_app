package apc.appcradle.pokemonstest.ui.screens._navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import apc.appcradle.pokemonstest.domain.models.PokemonAppState
import apc.appcradle.pokemonstest.ui.screens.main.MainScreen

fun NavController.toMainScreen() {
    navigate(route = Destinations.MainScreen.route)
}

fun NavGraphBuilder.mainScreen(
    state: PokemonAppState,
    searchPokemons: () -> Unit,
    onRefreshPull: () -> Unit
) {
    composable(Destinations.MainScreen.route) {
        MainScreen(
            state = state,
            searchPokemons = searchPokemons,
            onRefreshPull = onRefreshPull
        )
    }
}