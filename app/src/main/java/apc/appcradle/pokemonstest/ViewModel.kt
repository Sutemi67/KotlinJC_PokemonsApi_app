package apc.appcradle.pokemonstest

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import apc.appcradle.pokemonstest.data.LocalRepositoryImpl
import apc.appcradle.pokemonstest.domain.models.PokemonAppState
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

    fun fetchPokemon() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val newList = localRepository.fetchPokemonList()
            val oldList = state.value.list.toMutableList()
            val isSuccess = oldList.addAll(newList)
            if (isSuccess) {
                _state.update {
                    it.copy(
                        list = oldList,
                        isLoading = false
                    )
                }
            } else {
                _state.update {
                    it.copy(
                        isLoading = false
                    )
                }
            }
            Log.d("data", "viewmodel list: $oldList")
        }
    }
}