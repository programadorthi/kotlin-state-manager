package dev.programadorthi.common.state

import dev.programadorthi.common.api.Fact
import dev.programadorthi.common.api.NorrisApi
import dev.programadorthi.compose.BaseComposeValueManager
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Sample using inheritance and providing access to all public methods
 * to be consumed direct from the manager
 */
class FactsValueManager(
    private val norrisApi: NorrisApi,
    coroutineDispatcher: CoroutineDispatcher,
) : BaseComposeValueManager<State<List<Fact>>>(
    initialValue = State.Loading,
    coroutineDispatcher = coroutineDispatcher
) {
    /**
     * Sample using update function
     */
    suspend fun fetch(category: String) {
        update(State.Loading)
        runCatching {
            val result = norrisApi.facts(category)
            update(State.Success(result))
        }.onFailure { ex ->
            update(State.Error(ex))
        }
    }
}