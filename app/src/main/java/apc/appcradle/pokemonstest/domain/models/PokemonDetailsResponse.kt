package apc.appcradle.pokemonstest.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonDetailsResponse(
	@SerialName("sprites")
	val sprites: Sprites
)


