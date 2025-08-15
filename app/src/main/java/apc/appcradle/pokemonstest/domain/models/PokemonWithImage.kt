package apc.appcradle.pokemonstest.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class PokemonWithImage(
    /*
    Сюда можно добавить еще много полей из ответа по персональной ссылке покемона, и, передавая этот класс показывать нужную
    информацию на экранах, а так же фильтровать по ней.
    */
    val name: String,
    val personalDataUrl: String,
    val imageUrl: String?
)