package dev.programadorthi.state.compose

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import dev.programadorthi.state.compose.fake.FakeSaveableStateRegistry
import dev.programadorthi.state.compose.helper.runComposeTest
import dev.programadorthi.state.core.extension.getValue
import dev.programadorthi.state.core.extension.setValue
import kotlin.test.Test
import kotlin.test.assertEquals

internal class ComposeValueManagerTest {
    @Test
    fun shouldCurrentValueBeEqualsToInitialValue() = runComposeTest { composition, recomposer ->
        val manager = composeValueManager(0)
        var result = -1

        composition.setContent {
            val value by remember { manager }
            result = value
        }

        recomposer()

        assertEquals(0, result, "Current value is not equals to initial value")
    }

    @Test
    fun shouldCurrentValueBeEqualsToInitialValue_WhenUsingDelegateProperty() =
        runComposeTest { composition, recomposer ->
            val value by composeValueManager(0)
            var result = -1

            composition.setContent {
                result = value
            }

            recomposer()

            assertEquals(0, result, "Current value is not equals to initial value")
        }

    @Test
    fun shouldChangeCurrentValue_WhenCallUpdate() = runComposeTest { composition, recomposer ->
        val manager = composeValueManager(0)
        var result = -1

        composition.setContent {
            val value by remember { manager.asState() }
            result = value
        }

        manager.update { value ->
            value + 1
        }
        recomposer()

        assertEquals(1, result, "Call to update function is not updating current value")
    }

    @Test
    fun shouldChangeCurrentValue_WhenCallUpdateUsingDelegateProperty() =
        runComposeTest { composition, recomposer ->
            var value by composeValueManager(0)
            var result = -1

            composition.setContent {
                result = value
            }

            value += 1
            recomposer()

            assertEquals(1, result, "Updating by delegate property is not updating current value")
        }

    @Test
    fun shouldChangeCurrentValue_WhenCallRememberedVersion() =
        runComposeTest { composition, recomposer ->
            var update = false
            var result = -1

            composition.setContent {
                val (value, setValue) = remember { composeValueManager(0) }

                LaunchedEffect(update) {
                    setValue(value + 1)
                }

                result = value
            }

            update = true
            recomposer()

            assertEquals(1, result, "Updating by delegate property is not updating current value")
        }

    @Test
    fun shouldNotRegisterToRestorationWithoutAKey() {
        val stateRegistry = FakeSaveableStateRegistry()

        composeValueManager(
            initialValue = 0,
            stateRestorationKey = "",
            stateRegistry = stateRegistry,
        )

        assertEquals(
            "",
            stateRegistry.consumeRestoredKey,
            "Should not have a restored key when not provided"
        )
    }

    @Test
    fun shouldRegisterToRestoration() {
        val stateRestorationKey = "key#123"
        val stateRegistry = FakeSaveableStateRegistry()
        val expectKey = "dev.programadorthi.state.compose.ComposeValueManagerState:$stateRestorationKey"

        composeValueManager(
            initialValue = 0,
            stateRegistry = stateRegistry,
            stateRestorationKey = stateRestorationKey,
        )

        assertEquals(
            expectKey,
            stateRegistry.consumeRestoredKey,
            "Consuming different keys is wrong"
        )
        assertEquals(0, stateRegistry.canBeSavedValue, "Saved values different")
    }

    @Test
    fun shouldRegisterToRestorationByPropertyDelegate() {
        val stateRegistry = FakeSaveableStateRegistry()
        val myName by composeValueManager(
            initialValue = 0,
            stateRegistry = stateRegistry,
        )

        assertEquals(
            "dev.programadorthi.state.compose.ComposeValueManagerState:myName",
            stateRegistry.consumeRestoredKey,
            "Consuming different property keys is wrong"
        )
        assertEquals(0, stateRegistry.canBeSavedValue, "Saved values different")
        assertEquals(0, myName, "Saved values different in properties")
    }

    @Test
    fun shouldRegisterRestorationProvider() {
        val stateRestorationKey = "key#123"
        val stateRegistry = FakeSaveableStateRegistry()
        val expectKey = "dev.programadorthi.state.compose.ComposeValueManagerState:$stateRestorationKey"
        stateRegistry.canBeSaved = true

        composeValueManager(
            initialValue = 0,
            stateRegistry = stateRegistry,
            stateRestorationKey = stateRestorationKey,
        )

        assertEquals(
            expectKey,
            stateRegistry.consumeRestoredKey,
            "Consuming different keys is wrong"
        )
        assertEquals(0, stateRegistry.canBeSavedValue, "Saved values are different")
        assertEquals(
            expectKey,
            stateRegistry.entry?.key,
            "Registering providers with different keys"
        )
        assertEquals(0, stateRegistry.entry?.valueProvider?.invoke(), "Not provided correct value")
    }

    @Test
    fun shouldRestorePreviousValue() {
        val stateRestorationKey = "key#123"
        val stateRegistry = FakeSaveableStateRegistry()
        val expectKey = "dev.programadorthi.state.compose.ComposeValueManagerState:$stateRestorationKey"
        stateRegistry.canBeSaved = true
        stateRegistry.consumeRestored = 2024

        val manager = composeValueManager(
            initialValue = 0,
            stateRegistry = stateRegistry,
            stateRestorationKey = stateRestorationKey,
        )

        assertEquals(2024, manager.value, "Value not restored correctly")
        assertEquals(
            expectKey,
            stateRegistry.consumeRestoredKey,
            "Consuming different keys is wrong"
        )
        assertEquals(2024, stateRegistry.canBeSavedValue, "Different can be saved values")
        assertEquals(
            expectKey,
            stateRegistry.entry?.key,
            "Registering providers with different keys"
        )
        assertEquals(
            2024,
            stateRegistry.entry?.valueProvider?.invoke(),
            "Not provided correct value"
        )
    }
}