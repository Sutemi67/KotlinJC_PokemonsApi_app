package apc.appcradle.pokemonstest.domain

import apc.appcradle.pokemonstest.domain.models.Pokemon
import apc.appcradle.pokemonstest.domain.models.PokemonWithImage
import kotlinx.coroutines.flow.Flow

interface NetworkClient {
    fun fetchPokemonList(previousOffset: Int): Flow<PokemonWithImage>
    fun getImage(pokemon: Pokemon): Flow<String?>
}