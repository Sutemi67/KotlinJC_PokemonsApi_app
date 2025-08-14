package apc.appcradle.pokemonstest.data

import apc.appcradle.pokemonstest.domain.LocalRepository
import apc.appcradle.pokemonstest.domain.models.Pokemon

class LocalRepositoryImpl(
    private val networkClientImpl: NetworkClientImpl
) : LocalRepository {

    override suspend fun fetchPokemonList(): List<Pokemon> {
        val list = networkClientImpl.fetchPokemonList()
        return list
    }

}