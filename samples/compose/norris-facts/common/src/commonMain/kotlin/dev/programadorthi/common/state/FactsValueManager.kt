package dev.programadorthi.common.state

import androidx.compose.runtime.structuralEqualityPolicy
import dev.programadorthi.common.api.Fact
import dev.programadorthi.common.api.NorrisApi
import dev.programadorthi.state.core.BaseValueManager

/**
 * Sample using inheritance and providing access to all public methods
 * to be consumed direct from the manager
 */
class FactsValueManager(
    private val norrisApi: NorrisApi,
) : BaseValueManager<State<List<Fact>>>(
    initialValue = State.Loading,
    policy = structuralEqualityPolicy(),
) {
    /**
     * Sample using update function
     */
    suspend fun fetch(category: String) {
        value = State.Loading
        runCatching {
            val result = norrisApi.facts(category)
            value = State.Success(result)
        }.onFailure { ex ->
            value = State.Error(ex)
        }
    }
}