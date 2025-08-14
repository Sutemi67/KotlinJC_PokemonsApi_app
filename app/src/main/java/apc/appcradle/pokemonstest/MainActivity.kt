package apc.appcradle.pokemonstest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import apc.appcradle.pokemonstest.ui.theme.PokemonsTestTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val viewModel = koinViewModel<ViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            PokemonsTestTheme {
                PokemonsApp(viewModel, state)
            }
        }
    }
}