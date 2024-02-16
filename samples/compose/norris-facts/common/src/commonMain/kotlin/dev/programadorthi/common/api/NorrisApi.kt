package dev.programadorthi.common.api

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class NorrisApi {
    private val client = HttpClient(CIO) {
        install(Logging) {
            level = LogLevel.ALL
        }
        install(ContentNegotiation) {
            json(json = Json {
                encodeDefaults = true
                isLenient = true
                allowSpecialFloatingPointValues = true
                allowStructuredMapKeys = true
                prettyPrint = true
                useArrayPolymorphism = false
                ignoreUnknownKeys = true
            })
        }
    }

    fun close() {
        client.close()
    }

    suspend fun categories(): List<String> =
        client.get("https://api.chucknorris.io/jokes/categories").body()

    suspend fun facts(category: String): List<Fact> {
        val result: Result = client.get("https://api.chucknorris.io/jokes/search") {
            parameter("query", category)
        }.body()
        return result.result ?: emptyList()
    }
}