package apc.appcradle.pokemonstest.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import apc.appcradle.pokemonstest.R
import apc.appcradle.pokemonstest.domain.models.MainListElement
import apc.appcradle.pokemonstest.ui.theme.PokemonsTestTheme

@Composable
fun ListElementComposable(
    element: MainListElement
) {
    ElevatedCard {
        Box(
            modifier = Modifier.size(150.dp),
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

@Preview(showBackground = true)
@Composable
private fun Preview() {
    PokemonsTestTheme {
        ListElementComposable(MainListElement(name = "Bulba", description = "", image = ""))
    }
}