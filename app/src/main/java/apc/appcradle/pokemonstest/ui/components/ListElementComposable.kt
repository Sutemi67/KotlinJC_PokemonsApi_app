package apc.appcradle.pokemonstest.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import apc.appcradle.pokemonstest.domain.models.PokemonWithImage
import apc.appcradle.pokemonstest.ui.theme.PokemonsTestTheme
import coil3.compose.AsyncImage

@Composable
fun ListElementComposable(
    element: PokemonWithImage
) {
    ElevatedCard {
        Box(
            modifier = Modifier.aspectRatio(1f),
            contentAlignment = Alignment.BottomStart
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = element.imageUrl,
                contentDescription = "${element.name} image"
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = element.name,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    PokemonsTestTheme {
        ListElementComposable(
            PokemonWithImage(
                name = "Bulbazavr",
                imageUrl = "",
                personalDataUrl = ""
            )
        )
    }
}