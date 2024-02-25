package dev.programadorthi.state.compose

import androidx.compose.runtime.SnapshotMutationPolicy
import androidx.compose.runtime.saveable.SaveableStateRegistry
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.autoSaver
import androidx.compose.runtime.structuralEqualityPolicy
import dev.programadorthi.state.core.ValueManager
import dev.programadorthi.state.core.extension.basicValueManager
import kotlin.properties.PropertyDelegateProvider
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

public typealias ComposeValueManager<T> = PropertyDelegateProvider<Any?, ReadWriteProperty<Any?, T>>

public fun <T> composeValueManager(
    initialValue: T,
    policy: SnapshotMutationPolicy<T> = structuralEqualityPolicy(),
): ValueManager<T> = basicValueManager(initialValue).asState(policy = policy)

public fun <T> composeValueManager(
    initialValue: T,
    stateRegistry: SaveableStateRegistry,
    policy: SnapshotMutationPolicy<T> = structuralEqualityPolicy(),
    saver: Saver<T, out Any> = autoSaver(),
): ComposeValueManager<T> = basicValueManager(initialValue).asState(
    stateRegistry = stateRegistry,
    policy = policy,
    saver = saver,
)

public fun <T> composeValueManager(
    initialValue: T,
    stateRestorationKey: String,
    stateRegistry: SaveableStateRegistry,
    policy: SnapshotMutationPolicy<T> = structuralEqualityPolicy(),
    saver: Saver<T, out Any> = autoSaver(),
): ValueManager<T> = ComposeValueManagerState(
    valueManager = basicValueManager(initialValue),
    stateRestorationKey = stateRestorationKey,
    stateRegistry = stateRegistry,
    policy = policy,
    saver = saver,
)

public fun <T> ValueManager<T>.asState(
    policy: SnapshotMutationPolicy<T> = structuralEqualityPolicy(),
): ValueManager<T> = ComposeValueManagerState(
    valueManager = this,
    policy = policy,
    stateRegistry = null,
    stateRestorationKey = null,
    saver = autoSaver(),
)

public fun <T> ValueManager<T>.asState(
    stateRegistry: SaveableStateRegistry,
    policy: SnapshotMutationPolicy<T> = structuralEqualityPolicy(),
    saver: Saver<T, out Any> = autoSaver(),
): ComposeValueManager<T> = PropertyDelegateProvider { _, property ->
    val state = asState(
        stateRestorationKey = property.name,
        stateRegistry = stateRegistry,
        policy = policy,
        saver = saver,
    )
    object : ReadWriteProperty<Any?, T> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): T = state.value

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
            state.value = value
        }
    }
}

public fun <T> ValueManager<T>.asState(
    stateRestorationKey: String,
    stateRegistry: SaveableStateRegistry,
    policy: SnapshotMutationPolicy<T> = structuralEqualityPolicy(),
    saver: Saver<T, out Any> = autoSaver(),
): ValueManager<T> = ComposeValueManagerState(
    valueManager = this,
    policy = policy,
    stateRegistry = stateRegistry,
    stateRestorationKey = stateRestorationKey,
    saver = saver,
)
