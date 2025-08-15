package apc.appcradle.pokemonstest.data

import apc.appcradle.pokemonstest.domain.models.PokemonDAO
import apc.appcradle.pokemonstest.domain.models.PokemonWithImage

class LocalDatabaseConverter {
    fun convertToDaoPokemons(listOfPokemons: List<PokemonWithImage>): List<PokemonDAO> {
        return listOfPokemons.map { element ->
            PokemonDAO(
                name = element.name,
                personalUrl = element.personalDataUrl,
                imageUrl = element.imageUrl
            )
        }
    }

    fun convertToAppPokemons(listOfDaoPokemons: List<PokemonDAO>): List<PokemonWithImage> {
        return listOfDaoPokemons.map { element ->
            PokemonWithImage(
                name = element.name,
                personalDataUrl = element.personalUrl,
                imageUrl = element.imageUrl
            )
        }
    }
}