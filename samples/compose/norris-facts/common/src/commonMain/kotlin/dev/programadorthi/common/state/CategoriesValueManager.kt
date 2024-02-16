package dev.programadorthi.common.state

import dev.programadorthi.common.api.NorrisApi
import dev.programadorthi.state.core.extension.basicValueManager

/**
 * Sample without inheritance
 */
class CategoriesValueManager(
    private val norrisApi: NorrisApi,
) {
    val categories = basicValueManager<State<List<String>>>(initialValue = State.Loading)

    /**
     * Sample using delegate properties
     */
    suspend fun fetch() {
        categories.value = State.Loading
        runCatching {
            val result = norrisApi.categories()
            categories.value = State.Success(result)
        }.onFailure { ex ->
            categories.value = State.Error(ex)
        }
    }
}