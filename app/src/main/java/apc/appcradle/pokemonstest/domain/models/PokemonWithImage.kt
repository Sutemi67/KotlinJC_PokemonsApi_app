package apc.appcradle.pokemonstest.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class PokemonWithImage(
    val name: String,
    val personalDataUrl: String,
    val imageUrl: String?
)