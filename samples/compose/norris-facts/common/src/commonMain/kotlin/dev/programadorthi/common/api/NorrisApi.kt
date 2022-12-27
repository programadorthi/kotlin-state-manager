package dev.programadorthi.common.api

import io.ktor.client.HttpClient
import io.ktor.client.call.receive
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.expectSuccess
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.json.Json

class NorrisApi {
    private val client = HttpClient(CIO) {
        install(Logging) {
            level = LogLevel.ALL
        }
        install(JsonFeature) {
            serializer = KotlinxSerializer(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }
    }

    fun close() {
        client.close()
    }

    suspend fun categories(): List<String> =
        client.get("https://api.chucknorris.io/jokes/categories")

    suspend fun facts(category: String): List<Fact> {
        val result: Result = client.get("https://api.chucknorris.io/jokes/search") {
            parameter("query", category)
        }
        return result.result ?: emptyList()
    }
}