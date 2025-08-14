package apc.appcradle.pokemonstest.domain

import apc.appcradle.pokemonstest.domain.models.Pokemon
import apc.appcradle.pokemonstest.domain.models.PokemonListResponse

interface LocalRepository {
    suspend fun fetchPokemonList(): List<Pokemon>
}