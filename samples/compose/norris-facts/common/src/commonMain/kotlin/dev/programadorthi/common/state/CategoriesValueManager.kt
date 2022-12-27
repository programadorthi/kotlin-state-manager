package dev.programadorthi.common.state

import dev.programadorthi.common.api.NorrisApi
import dev.programadorthi.compose.extension.composeValueManager
import dev.programadorthi.core.extension.getValue
import dev.programadorthi.core.extension.setValue

/**
 * Sample without inheritance
 */
class CategoriesValueManager(
    private val norrisApi: NorrisApi,
) {
    var categories by composeValueManager<State<List<String>>>(initialValue = State.Loading)
        private set

    /**
     * Sample using delegate properties
     */
    suspend fun fetch() {
        categories = State.Loading
        runCatching {
            val result = norrisApi.categories()
            categories = State.Success(result)
        }.onFailure { ex ->
            categories = State.Error(ex)
        }
    }
}