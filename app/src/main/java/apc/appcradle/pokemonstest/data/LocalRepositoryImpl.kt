package apc.appcradle.pokemonstest.data

import android.util.Log
import apc.appcradle.pokemonstest.domain.LocalRepository
import apc.appcradle.pokemonstest.domain.models.PokemonWithImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LocalRepositoryImpl(
    private val networkClientImpl: NetworkClientImpl,
    private val database: LocalDatabase,
    private val converter: LocalDatabaseConverter
) : LocalRepository {
    private var previousList = mutableListOf<PokemonWithImage>()
    val dao = database.userDao()

    override suspend fun fetchPokemonList(previousListSize: Int): Flow<List<PokemonWithImage>> =
        flow {
            val newList = networkClientImpl.fetchPokemonList(previousListSize)
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

    fun writeToDb(listOfPokemons: List<PokemonWithImage>) {
        clearBd() //todo для отладки
        val listOfDao = converter.convertToDaoPokemons(listOfPokemons)
        dao.insertAll(listOfDao)
        previousList.clear()
        previousList.addAll(listOfPokemons)
    }

    fun readFromDb(): List<PokemonWithImage> {
        val listOfDao = dao.getAll()
        val listOfPokemons = converter.convertToAppPokemons(listOfDao)
//        previousList.addAll(listOfPokemons)
        return listOfPokemons
    }

    fun clearBd() {
        dao.clearDb()
        previousList.clear()
    }
}