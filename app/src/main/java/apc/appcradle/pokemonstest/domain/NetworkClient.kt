package apc.appcradle.pokemonstest.domain

import apc.appcradle.pokemonstest.domain.models.Pokemon

interface NetworkClient {
    suspend fun fetchPokemonList(): List<Pokemon>
    suspend fun getDetails(id: Int)
//    suspend fun loadMore()
}