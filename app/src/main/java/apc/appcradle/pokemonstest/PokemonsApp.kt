package apc.appcradle.pokemonstest

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import apc.appcradle.pokemonstest.domain.models.PokemonAppState
import apc.appcradle.pokemonstest.ui.components.AppTopBar
import apc.appcradle.pokemonstest.ui.screens._navigation.Destinations
import apc.appcradle.pokemonstest.ui.screens._navigation.mainScreen

@Composable
fun PokemonsApp(
    viewModel: ViewModel,
    state: PokemonAppState
) {
    val navController = rememberNavController()
    LaunchedEffect(Unit) {
        viewModel.fetchPokemon()
    }
    Scaffold(
        topBar = { AppTopBar() },
        bottomBar = {}
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            startDestination = Destinations.MainScreen.route,
            navController = navController
        ) {
            mainScreen(state, searchPokemons = { viewModel.fetchPokemon() })
        }
    }
}
