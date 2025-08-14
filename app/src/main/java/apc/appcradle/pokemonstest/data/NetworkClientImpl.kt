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
import kotlinx.serialization.json.Json

class NetworkClientImpl() : NetworkClient {
    private val mainUrl = "https://pokeapi.co/api/v2"
    private var currentOffset = 0
    private var totalCount = 0
    private val pageSize: Int = 20
    private var isLastPage = false

    private val imageCache = mutableMapOf<String, String>()

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    override suspend fun fetchPokemonList(): List<PokemonWithImage> {
        try {
            if (isLastPage) return emptyList()
            val request = client.get("$mainUrl/pokemon") {
                parameter("limit", pageSize)
                parameter("offset", currentOffset)
            }
            val response: PokemonListResponse = request.body()
            totalCount = response.count
            currentOffset += pageSize
            isLastPage = currentOffset >= totalCount
            val list = mutableListOf<PokemonWithImage>()
            response.results.forEach { pokemon ->
                list.add(getImage(pokemon))
            }
            Log.d("data", "networkClient success:\n$list\n$pageSize\n$currentOffset")
            return list
        } catch (e: Exception) {
            Log.e("data", "networkClient error: $e")
            return emptyList()
        }
    }

    override suspend fun getImage(pokemon: Pokemon): PokemonWithImage {
        imageCache[pokemon.name]?.let {
            Log.i("data", "networkClient: get image from cache")
            return PokemonWithImage(pokemon.name, it)
        }
        return try {
            val detailsResponse: PokemonDetailsResponse = client.get(pokemon.url).body()
            val imageUrl = detailsResponse.sprites.frontDefault
            PokemonWithImage(
                name = pokemon.name,
                imageUrl = imageUrl
            )
        } catch (e: Exception) {
            Log.e("data", "networkClient.getImage: Error fetching details for ${pokemon.name}: $e")
            PokemonWithImage(pokemon.name, null)
        }
    }
}