package apc.appcradle.pokemonstest

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import apc.appcradle.pokemonstest.data.LocalRepositoryImpl
import apc.appcradle.pokemonstest.domain.models.PokemonAppState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ViewModel(
    private val localRepository: LocalRepositoryImpl
) : ViewModel() {

    private var _state = MutableStateFlow(PokemonAppState())
    val state: StateFlow<PokemonAppState> = _state.asStateFlow()

    private var searchJob: Job? = null

    /*
        init {
        получаем все сохраненные настройки приложения, если таковые будем заводить в будущем
            getAppThemeFromPreferences()

        чек интернета и дальше от статуса работа с бд или апи
            checkInternetConnectionState()
        }
    */

    fun fetchPokemon() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val newListFlow = localRepository.fetchPokemonList()
            newListFlow.collect { newList ->
                _state.update {
                    it.copy(
                        list = newList,
                        isLoading = false
                    )
                }
                Log.d("data", "viewmodel list size: ${newList.size}")
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
            Log.d("data", "viewmodel unfiltered list size: ${state.value.list.size}")

        } else {
            searchJob = viewModelScope.launch {
                delay(2000)
                _state.update {
                    it.copy(
                        searchFilterText = searchText,
                        list = localRepository.filterList(searchText)
                    )
                }
                Log.d("data", "viewmodel filtered list size: ${state.value.list.size}")
            }
        }
    }

    override fun onCleared() {
        searchJob?.cancel()
        searchJob = null
        super.onCleared()
    }
}