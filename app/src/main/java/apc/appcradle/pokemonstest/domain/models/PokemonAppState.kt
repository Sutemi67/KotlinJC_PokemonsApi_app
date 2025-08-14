package apc.appcradle.pokemonstest.domain.models

data class PokemonAppState(
    //data
    val list: List<PokemonWithImage> = emptyList(),
    val searchFilterText: String? = null,

    //state
    val isLoading: Boolean = false
)
