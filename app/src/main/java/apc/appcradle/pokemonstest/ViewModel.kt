package apc.appcradle.pokemonstest

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import apc.appcradle.pokemonstest.data.LocalRepositoryImpl
import apc.appcradle.pokemonstest.domain.models.PokemonAppState
import apc.appcradle.pokemonstest.domain.models.PokemonWithImage
import apc.appcradle.pokemonstest.domain.models.SearchType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewModel(
    private val localRepository: LocalRepositoryImpl
) : ViewModel() {

    private var _state = MutableStateFlow(PokemonAppState())
    val state: StateFlow<PokemonAppState> = _state.asStateFlow()

    private var searchJob: Job? = null
    private var writeToDbJob: Job? = null
    private var fetchJob: Job? = null

    /*
        init {
        получаем все сохраненные настройки приложения, если таковые будем заводить в будущем
            getAppThemeFromPreferences()

        чек интернета и дальше от статуса работа с бд или апи
            checkInternetConnectionState()
        }
    */

    fun clearDb() {
        fetchJob?.cancel()
        writeToDbJob?.cancel()
        viewModelScope.launch(Dispatchers.IO) { localRepository.clearBd() }
        _state.update {
            it.copy(
                list = emptyList(),
                searchType = SearchType.Initial
            )
        }
    }

    fun fetchPokemon() {
        if (state.value.isLoading) {
            Log.e("data", "viewmodel already loading, skipping...")
            return
        }

        Log.i("data", "viewmodel fetching pokes")
        _state.update { it.copy(isLoading = true) }

        fetchJob?.cancel()
        fetchJob = viewModelScope.launch(Dispatchers.IO) {

            val dbList: List<PokemonWithImage>? =
                withContext(Dispatchers.IO) { localRepository.readFromDb() }
            val currentListSize =
                if (state.value.searchType == SearchType.Initial) {
                    dbList?.size ?: 0
                } else state.value.list.size

            if (dbList.isNullOrEmpty() || state.value.searchType == SearchType.GetMore) {
                Log.d("data", "viewmodel dblistsize: ${dbList?.size}")
                val newListFlow = localRepository.fetchPokemonList(currentListSize)
                newListFlow.collect { newList ->
                    writeToDbJob?.cancel()
                    writeToDbJob = viewModelScope.launch(Dispatchers.IO) {
                        delay(3000)
                        localRepository.writeToDb(newList)
                        Log.d("data", "viewmodel write to db successful")
                    }
                    _state.update { it.copy(list = newList) }
                    Log.d("data", "viewmodel list size: ${newList.size}")
                }
                Log.i("data", "viewmodel collect end")
                _state.update {
                    it.copy(
                        isLoading = false,
                        searchType = SearchType.GetMore
                    )
                }
            } else {
                _state.update {
                    it.copy(
                        list = dbList,
                        isLoading = false,
                        searchType = SearchType.GetMore
                    )
                }
                Log.e("data", "viewmodel database list read successful")
            }
        }
    }


    fun onNewSearchTextEntered(searchText: String?) {
        searchJob?.cancel()
        if (searchText.isNullOrEmpty()) {
            _state.update {
                it.copy(
                    searchFilterText = null,
                    list = localRepository.getUnfilteredList()
                )
            }

        } else {
            searchJob = viewModelScope.launch {
                delay(2000)
                _state.update {
                    it.copy(
                        searchFilterText = searchText,
                        list = localRepository.filterList(searchText)
                    )
                }
            }
        }
    }

    fun onRefreshPull() {
        clearDb()
        fetchPokemon()
    }

    override fun onCleared() {
        searchJob?.cancel()
        searchJob = null
        fetchJob = null
        writeToDbJob = null
        super.onCleared()
    }
}