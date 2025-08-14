package apc.appcradle.pokemonstest.domain.models

data class PokemonAppState(
    //data
    val list: List<Pokemon> = emptyList(),

    //state
    val isLoading: Boolean = false
)
