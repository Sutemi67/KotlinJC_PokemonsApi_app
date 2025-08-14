package apc.appcradle.pokemonstest.domain

import apc.appcradle.pokemonstest.domain.models.Pokemon
import apc.appcradle.pokemonstest.domain.models.PokemonWithImage

interface NetworkClient {
    suspend fun fetchPokemonList(): List<PokemonWithImage>
    suspend fun getImage(pokemon: Pokemon): PokemonWithImage
}