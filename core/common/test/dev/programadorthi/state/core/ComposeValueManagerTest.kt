package dev.programadorthi.state.core

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dev.programadorthi.state.core.compose.runComposeTest
import dev.programadorthi.state.core.extension.basicValueManager
import dev.programadorthi.state.core.extension.rememberBasicValueManager
import kotlin.test.Test
import kotlin.test.assertEquals

internal class ComposeValueManagerTest {
    @Test
    fun shouldCurrentValueBeEqualsToInitialValue() = runComposeTest { composition, recomposer ->
        val manager = basicValueManager(0)
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
            val value by basicValueManager(0)
            var result = -1

            composition.setContent {
                result = value
            }

            recomposer()

            assertEquals(0, result, "Current value is not equals to initial value")
        }

    @Test
    fun shouldChangeCurrentValue_WhenCallUpdate() = runComposeTest { composition, recomposer ->
        val manager = basicValueManager(0)
        var result = -1

        composition.setContent {
            val value by remember { manager }
            result = value
        }

        manager.update { value ->
            value + 1
        }
        recomposer()

        assertEquals(1, result, "Call to update function is not updating current value")
    }

    @Test
    fun shouldChangeCurrentValue_WhenCallUpdateUsingDelegateProperty() = runComposeTest { composition, recomposer ->
        var value by basicValueManager(0)
        var result = -1

        composition.setContent {
            result = value
        }

        value += 1
        recomposer()

        assertEquals(1, result, "Updating by delegate property is not updating current value")
    }

    @Test
    fun shouldChangeCurrentValue_WhenCallRememberedVersion() = runComposeTest { composition, recomposer ->
        var update = false
        var result = -1

        composition.setContent {
            var value by rememberBasicValueManager(0)

            LaunchedEffect(update) {
                value++
            }

            result = value
        }

        update = true
        recomposer()

        assertEquals(1, result, "Updating by delegate property is not updating current value")
    }
}