package apc.appcradle.pokemonstest.ui.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import apc.appcradle.pokemonstest.R
import apc.appcradle.pokemonstest.domain.models.Pokemon
import apc.appcradle.pokemonstest.domain.models.PokemonAppState
import apc.appcradle.pokemonstest.ui.theme.PokemonsTestTheme
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@Composable
fun MainScreen(
    state: PokemonAppState,
    searchPokemons: () -> Unit
) {
    val gridState = rememberLazyGridState()

    LaunchedEffect(Unit) {
        searchPokemons()
    }

    LaunchedEffect(gridState) {
        snapshotFlow { gridState.layoutInfo }
            .map { layoutInfo ->
                val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
                lastVisibleItem?.index == layoutInfo.totalItemsCount - 1
            }
            .distinctUntilChanged()
            .collect { reachedEnd -> if (reachedEnd) searchPokemons() }
    }
    if (state.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(modifier = Modifier.size(200.dp))
        }
    } else {
        if (state.list.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Nothing found")
            }
        } else {
            val list = state.list
            LazyVerticalGrid(
                state = gridState,
                modifier = Modifier.padding(10.dp),
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(list) { element ->
                    Box(
                        modifier = Modifier.aspectRatio(1f),
                        contentAlignment = Alignment.BottomStart
                    ) {
                        Image(
                            modifier = Modifier.fillMaxSize(),
                            painter = painterResource(R.drawable.ic_launcher_background),
                            contentDescription = "pokemon image"
                        )
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = element.name,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun GreetingPreview() {
    PokemonsTestTheme {
        MainScreen(
            searchPokemons = {},
            state = PokemonAppState(
                list = listOf(
                    Pokemon(name = "Bulba", url = ""),
                    Pokemon(name = "Bulba", url = ""),
                    Pokemon(name = "Bulba", url = ""),
                    Pokemon(name = "Bulba", url = ""),
                )
            )
        )
    }
}