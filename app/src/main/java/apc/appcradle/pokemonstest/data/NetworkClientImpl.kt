package apc.appcradle.pokemonstest.data

import android.util.Log
import apc.appcradle.pokemonstest.domain.NetworkClient
import apc.appcradle.pokemonstest.domain.models.Pokemon
import apc.appcradle.pokemonstest.domain.models.PokemonDetailsResponse
import apc.appcradle.pokemonstest.domain.models.PokemonListResponse
import apc.appcradle.pokemonstest.domain.models.PokemonWithImage
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.json.Json

class NetworkClientImpl() : NetworkClient {

    private val mainUrl = "https://pokeapi.co/api/v2"

    private var totalCount = 0
    private val pageSize: Int = 20
    private var isLastPage = false

    private val imageCache = mutableMapOf<String, String>()

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    override fun fetchPokemonList(previousOffset: Int): Flow<PokemonWithImage> = flow {
        try {
            if (isLastPage) return@flow

            val request = client.get("$mainUrl/pokemon") {
                parameter("limit", pageSize)
                parameter("offset", previousOffset)
            }
            val response: PokemonListResponse = request.body()
            totalCount = response.count

            if (previousOffset >= totalCount) {
                Log.d("data", "NetworkClient: достигнут конец списка (total: $totalCount)")
                return@flow
            }
                /*
                Данный слушатель потока создает класс только с данными полями, таким же образом можно
                создать либо методы, либо уже по имеющейся информации добавить поля (например, другие картинки
                из метода getImage()
                 */
            response.results.forEach { pokemon ->
                getImage(pokemon).collect { imageUrl ->
                    emit(
                        PokemonWithImage(
                            name = pokemon.name,
                            personalDataUrl = pokemon.url,
                            imageUrl = imageUrl
                        )
                    )
                }
            }
        } catch (e: Exception) {
            Log.e("data", "networkClient.fetch: Error fetching details: $e")
        }
    }.flowOn(Dispatchers.IO)

    override fun getImage(pokemon: Pokemon): Flow<String?> = flow {
        imageCache[pokemon.name]?.let { imageUrl ->
            Log.i("data", "networkClient: get image from cache")
            emit(imageUrl)
        }
        try {
            val detailsResponse: PokemonDetailsResponse = client.get(pokemon.url).body()

            //Берем только одну картинку, можно удалить остальные поля класса, либо использовать остальные картинки.
            val imageUrl = detailsResponse.sprites.frontDefault
            emit(imageUrl)
        } catch (e: Exception) {
            Log.e("data", "networkClient.getImage: Error fetching details for ${pokemon.name}: $e")
        }
    }.flowOn(Dispatchers.IO)
}