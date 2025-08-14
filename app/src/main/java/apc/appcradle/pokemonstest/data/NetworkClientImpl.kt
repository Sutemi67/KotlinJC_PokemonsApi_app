package apc.appcradle.pokemonstest.data

import android.util.Log
import apc.appcradle.pokemonstest.domain.NetworkClient
import apc.appcradle.pokemonstest.domain.models.Pokemon
import apc.appcradle.pokemonstest.domain.models.PokemonListResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.serialization.kotlinx.json.json

class NetworkClientImpl() : NetworkClient {
    private val mainUrl = "https://pokeapi.co/api/v2"
    private var currentOffset = 0
    private var totalCount = 0
    private val pageSize: Int = 20
    private var isLastPage = false

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    override suspend fun fetchPokemonList(): List<Pokemon> {
        try {
            if (isLastPage) return emptyList()
            val request = client.get("https://pokeapi.co/api/v2/pokemon") {
                parameter("limit", pageSize)
                parameter("offset", currentOffset)
            }
            val response: PokemonListResponse = request.body()
            totalCount = response.count
            currentOffset += pageSize
            isLastPage = currentOffset >= totalCount
            return response.results
        } catch (e: Exception) {
            Log.e("data", "networkClient: $e")
            return emptyList()
        }
    }

    override suspend fun getDetails(id: Int) {
        TODO("Not yet implemented")
    }
}