package apc.appcradle.pokemonstest.domain

import apc.appcradle.pokemonstest.domain.models.PokemonWithImage

interface LocalRepository {
    suspend fun fetchPokemonList(): List<PokemonWithImage>
    suspend fun filterList(searchText: String): List<PokemonWithImage>
    fun getUnfilteredList():List<PokemonWithImage>
}