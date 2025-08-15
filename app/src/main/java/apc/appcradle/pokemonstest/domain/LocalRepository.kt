package apc.appcradle.pokemonstest.domain

import apc.appcradle.pokemonstest.domain.models.PokemonWithImage
import kotlinx.coroutines.flow.Flow

interface LocalRepository {
    suspend fun fetchPokemonList(): Flow<List<PokemonWithImage>>
    suspend fun filterList(searchText: String): List<PokemonWithImage>
    fun getUnfilteredList(): List<PokemonWithImage>
}