package apc.appcradle.pokemonstest.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import apc.appcradle.pokemonstest.R
import apc.appcradle.pokemonstest.ui.theme.PokemonsTestTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    isBackButtonNeeded: Boolean,
    onBackButtonPressed: () -> Unit,
    onFilterPressed: () -> Unit,
    onValueChange: (String) -> Unit,
    onClearPressed: () -> Unit
) {
    var value by remember { mutableStateOf("") }

    Column(
        Modifier.padding(vertical = 20.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Image(
                modifier = Modifier.fillMaxWidth(0.5f),
                painter = painterResource(R.drawable.international_pokemon_logo_svg),
                contentDescription = "back icon"
            )
        }
        TopAppBar(
            title = {
                Column(
                    modifier = Modifier.height(200.dp)
                ) {

                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            modifier = Modifier.weight(1f),
                            value = value,
                            onValueChange = {
                                value = it
                                onValueChange(value)
                            },
                            shape = RoundedCornerShape(10.dp),
                            trailingIcon = {
                                if (!value.isEmpty()) {
                                    Icon(
                                        modifier = Modifier.clickable {
                                            value = ""
                                            onValueChange("")
                                        },
                                        imageVector = Icons.Default.Clear,
                                        contentDescription = "clear"
                                    )
                                }
                            },
                        )
                        IconButton(
                            onClick = onFilterPressed
                        ) {
                            Icon(
                                modifier = Modifier.fillMaxHeight(),
                                imageVector = Icons.AutoMirrored.Filled.List,
                                contentDescription = "filter"
                            )
                        }
                        IconButton(
                            onClick = onClearPressed
                        ) {
                            Icon(
                                modifier = Modifier.fillMaxHeight(),
                                imageVector = Icons.Default.Clear,
                                contentDescription = "filter"
                            )
                        }
                    }
                }
            },
            navigationIcon = {
                if (isBackButtonNeeded)
                    IconButton(
                        onClick = onBackButtonPressed
                    ) {
                        Icon(
                            modifier = Modifier.aspectRatio(1f),
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "back icon"
                        )
                    }
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PokemonsTestTheme {
        AppTopBar(
            isBackButtonNeeded = true,
            onBackButtonPressed = {},
            onFilterPressed = {},
            onValueChange = {},
            onClearPressed = {})
    }
}