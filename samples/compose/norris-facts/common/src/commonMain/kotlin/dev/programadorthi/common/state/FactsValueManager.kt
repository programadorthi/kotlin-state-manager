package dev.programadorthi.common.state

import dev.programadorthi.common.api.Fact
import dev.programadorthi.common.api.NorrisApi
import dev.programadorthi.state.core.BaseValueManager

class FactsValueManager(
    private val norrisApi: NorrisApi,
) : BaseValueManager<State<List<Fact>>>(
    initialValue = State.Loading,
) {
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