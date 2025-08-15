package apc.appcradle.pokemonstest.ui.screens.main

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import apc.appcradle.pokemonstest.domain.models.PokemonAppState
import apc.appcradle.pokemonstest.domain.models.PokemonWithImage
import apc.appcradle.pokemonstest.ui.components.ListElementComposable
import apc.appcradle.pokemonstest.ui.theme.PokemonsTestTheme
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@Composable
fun MainScreen(
    state: PokemonAppState,
    searchPokemons: () -> Unit
) {
    val gridState = rememberLazyGridState()

    if (state.searchFilterText.isNullOrEmpty())
        LaunchedEffect(gridState) {
            snapshotFlow { gridState.layoutInfo }
                .map { layoutInfo ->
                    val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
                    lastVisibleItem?.index == layoutInfo.totalItemsCount - 1
                }
                .distinctUntilChanged()
                .collect { reachedEnd -> if (reachedEnd) searchPokemons() }
        }
    if (state.list.isEmpty() && !state.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Can't find something")
        }
    } else {
        val list = state.list
        Log.i("data", "screen listsize: ${list.size}")
        Box(
            Modifier
                .fillMaxSize()
                .padding(10.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            LazyVerticalGrid(
                state = gridState,
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(items = list, key = { it.name }) { element ->
                    ListElementComposable(element)
                }
            }
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(100.dp))
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
                    PokemonWithImage(
                        name = "bulbasaur",
                        personalDataUrl = "",
                        imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png"
                    ),
                    PokemonWithImage(
                        name = "ivysaur",
                        personalDataUrl = "",

                        imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/2.png"
                    ),
                    PokemonWithImage(
                        name = "venusaur",
                        personalDataUrl = "",

                        imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/3.png"
                    ),
                )
            )
        )
    }
}