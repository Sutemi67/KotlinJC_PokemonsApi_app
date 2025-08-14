package apc.appcradle.pokemonstest.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Sprites(
	@SerialName("back_default")
	var backDefault: String? = null, // https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/20.png
	@SerialName("back_female")
	var backFemale: String? = null, // https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/female/20.png
	@SerialName("back_shiny")
	var backShiny: String? = null, // https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/shiny/20.png
	@SerialName("back_shiny_female")
	var backShinyFemale: String? = null, // https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/shiny/female/20.png
	@SerialName("front_default")
	var frontDefault: String? = null, // https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/20.png
	@SerialName("front_female")
	var frontFemale: String? = null, // https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/female/20.png
	@SerialName("front_shiny")
	var frontShiny: String? = null, // https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/20.png
	@SerialName("front_shiny_female")
	var frontShinyFemale: String? = null, // https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/female/20.png
)