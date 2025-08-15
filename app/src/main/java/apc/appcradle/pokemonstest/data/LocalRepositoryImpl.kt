package apc.appcradle.pokemonstest.data

import android.util.Log
import apc.appcradle.pokemonstest.domain.LocalRepository
import apc.appcradle.pokemonstest.domain.models.PokemonWithImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.last

class LocalRepositoryImpl(
    private val networkClientImpl: NetworkClientImpl
) : LocalRepository {
    private var previousList = mutableListOf<PokemonWithImage>()

    override suspend fun fetchPokemonList(): Flow<List<PokemonWithImage>> = flow {
        val newList = networkClientImpl.fetchPokemonList()
        Log.i("data", "repository new listsize: ${previousList.size}")
        newList.collect { pokemon ->
            previousList.add(pokemon)
            emit(previousList.toList())
        }

        //todo создать дата класс с сообщением об ошибке и возвращать его
    }.flowOn(Dispatchers.IO)

    override suspend fun filterList(searchText: String): List<PokemonWithImage> {
        val filteredList = previousList.filter { element ->
            element.name.contains(searchText)
        }
        Log.i("data", "repository filtered listsize: ${filteredList.size}")
        return filteredList
    }

    override fun getUnfilteredList(): List<PokemonWithImage> {
        Log.i("data", "repository previous listsize: ${previousList.size}")
        return previousList.toList()
    }
}