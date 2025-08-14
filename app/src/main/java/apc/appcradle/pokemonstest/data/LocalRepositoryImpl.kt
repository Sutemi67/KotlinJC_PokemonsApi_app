package apc.appcradle.pokemonstest.data

import android.util.Log
import apc.appcradle.pokemonstest.domain.LocalRepository
import apc.appcradle.pokemonstest.domain.models.PokemonWithImage

class LocalRepositoryImpl(
    private val networkClientImpl: NetworkClientImpl
) : LocalRepository {
    private var previousList = mutableListOf<PokemonWithImage>()

    override suspend fun fetchPokemonList(): List<PokemonWithImage> {
		val newList = networkClientImpl.fetchPokemonList()
		previousList.addAll(newList)
		Log.i("data", "repository new listsize: ${previousList.size}")
		return previousList.toList()
        //todo создать дата класс с сообщением об ошибке и возвращать его
    }

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