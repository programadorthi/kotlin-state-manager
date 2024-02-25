package dev.programadorthi.state.compose.fake

import androidx.compose.runtime.saveable.SaveableStateRegistry

internal class FakeSaveableStateRegistry : SaveableStateRegistry {
    var entry: FakeEntry? = null

    var canBeSaved: Boolean = false
    var canBeSavedValue: Any? = null
        private set

    var consumeRestoredKey: String = ""
        private set
    var consumeRestored: Any? = null

    val valueToSave: Map<String, List<Any?>> = mutableMapOf()

    override fun canBeSaved(value: Any): Boolean {
        canBeSavedValue = value
        return canBeSaved
    }

    override fun consumeRestored(key: String): Any? {
        consumeRestoredKey = key
        return consumeRestored
    }

    override fun performSave(): Map<String, List<Any?>> = valueToSave

    override fun registerProvider(
        key: String,
        valueProvider: () -> Any?
    ): SaveableStateRegistry.Entry {
        entry = FakeEntry(
            key = key,
            valueProvider = valueProvider
        )
        return entry!!
    }
}