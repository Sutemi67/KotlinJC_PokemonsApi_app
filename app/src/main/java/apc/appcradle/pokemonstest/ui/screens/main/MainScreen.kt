package apc.appcradle.pokemonstest.ui.screens.main

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import apc.appcradle.pokemonstest.domain.models.PokemonAppState
import apc.appcradle.pokemonstest.domain.models.SearchType
import apc.appcradle.pokemonstest.ui.components.ListElementComposable
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@Composable
fun MainScreen(
    state: PokemonAppState,
    searchPokemons: () -> Unit,
) {
    val gridState = rememberLazyGridState()

    if (state.searchFilterText.isNullOrEmpty())
        LaunchedEffect(key1 = gridState, key2 = state) {
            snapshotFlow { gridState.layoutInfo }
                .map { layoutInfo ->
                    val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
//                    lastVisibleItem?.index == layoutInfo.totalItemsCount - 1
                    val totalItems = layoutInfo.totalItemsCount
                    val lastVisibleIndex = lastVisibleItem?.index ?: 0

                    lastVisibleIndex >= totalItems - 2 && totalItems > 0
                }
                .distinctUntilChanged()
                .collect { reachedEnd ->
                    if (reachedEnd &&
                        state.list.isNotEmpty() &&
                        !state.isLoading &&
                        state.searchType == SearchType.GetMore
                    ) {
                        searchPokemons()
                        Log.d("data", "End of list starts pagination")
                    }
                }
        }
    if (state.list.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("List is empty")
                ElevatedButton(onClick = { searchPokemons() }) { Text("Retry") }
            }
        }
    } else {
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
                items(
                    items = state.list,
                    key = { it.name }
                ) { element ->
                    ListElementComposable(element)
                }
                if (state.isLoading) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(modifier = Modifier.size(32.dp))
                        }
                    }
                }
            }
        }
    }
}