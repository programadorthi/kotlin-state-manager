package dev.programadorthi.state.compose

import androidx.compose.runtime.RememberObserver
import androidx.compose.runtime.SnapshotMutationPolicy
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.SaveableStateRegistry
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import dev.programadorthi.state.core.ValueManager

internal class ComposeValueManagerState<T>(
    private val valueManager: ValueManager<T>,
    private val policy: SnapshotMutationPolicy<T>,
    private val stateRegistry: SaveableStateRegistry?,
    private val stateRestorationKey: String?,
    saver: Saver<T, out Any>,
) : SaverScope, RememberObserver, ValueManager<T> by valueManager {

    private val valueManagerSaver = ValueManagerSaver(saver) { valueManager }
    private val state = mutableStateOf(valueManager.value, policy)
    private val key = "$KEY:$stateRestorationKey"

    private var entry: SaveableStateRegistry.Entry? = null

    init {
        valueManager.collect { newValue ->
            state.value = newValue
        }
        tryRestoreAndRegister()
    }

    override var value: T
        get() = state.value
        set(value) {
            val current = state.value
            if (policy.equivalent(current, value).not()) {
                valueManager.value = value
            }
        }

    override fun canBeSaved(value: Any): Boolean {
        val registry = stateRegistry
        return registry == null || registry.canBeSaved(value)
    }

    override fun onAbandoned() {
        entry?.unregister()
    }

    override fun onForgotten() {
        entry?.unregister()
    }

    override fun onRemembered() {
        register()
    }

    private fun tryRestoreAndRegister() {
        if (stateRestorationKey.isNullOrBlank()) return
        val registry = stateRegistry ?: return
        registry.consumeRestored(key)?.let { consumed ->
            valueManagerSaver.restore(consumed)
        }

        register()
    }

    private fun register() {
        if (stateRestorationKey.isNullOrBlank()) return
        val registry = stateRegistry ?: return

        val saveable = {
            with(valueManagerSaver) {
                save(valueManager)
            }
        }
        val toSave = requireNotNull(saveable()) {
            "$value cannot be saved using the current SaveableStateRegistry"
        }
        if (registry.canBeSaved(toSave)) {
            entry = registry.registerProvider(key, saveable)
        }
    }

    private companion object {
        private const val KEY = "dev.programadorthi.state.compose.ComposeValueManagerState"
    }
}