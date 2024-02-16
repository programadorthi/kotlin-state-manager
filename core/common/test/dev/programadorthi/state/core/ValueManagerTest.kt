package dev.programadorthi.state.core

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import dev.programadorthi.state.core.extension.basicValueManager
import dev.programadorthi.state.core.fake.ErrorHandlerFake
import dev.programadorthi.state.core.fake.LifecycleHandlerFake
import dev.programadorthi.state.core.validation.Validator
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs

internal class ValueManagerTest {
    @Test
    fun shouldCurrentValueBeEqualsToInitialValue() {
        val manager = basicValueManager(0)
        assertEquals(0, manager.value, "Current value is not equals to initial value")
    }

    @Test
    fun shouldCurrentValueBeEqualsToInitialValue_WhenUsingDelegateProperty() {
        val value by basicValueManager(0)
        assertEquals(0, value, "Current value is not equals to initial value")
    }

    @Test
    fun shouldChangeCurrentValue_WhenCallUpdate() {
        val manager = basicValueManager(0)
        manager.update { value ->
            value + 1
        }
        assertEquals(1, manager.value, "Call to update function is not updating current value")
    }

    @Test
    fun shouldChangeCurrentValue_WhenCallUpdateUsingDelegateProperty() {
        var value by basicValueManager(0)
        value++ // or value = value + 1
        assertEquals(1, value, "Updating by delegate property is not updating current value")
    }

    @Test
    fun shouldNotInitiateClosed() {
        val manager = basicValueManager(0)
        assertEquals(false, manager.closed, "Value manager has started in closed state")
    }

    @Test
    fun shouldCloseAfterRequestedToClose() {
        val manager = basicValueManager(0)
        manager.close()
        assertEquals(true, manager.closed, "Value manager still opened after request to close")
    }

    @Test
    fun shouldCollectAllEmittedValue() {
        val expected = listOf(1, 2, 3, 4, 5)
        val result = mutableListOf<Int>()

        val manager = basicValueManager(0)
        manager.collect(result::add)
        repeat(times = 5) {
            manager.update { value ->
                value + 1
            }
        }

        assertContentEquals(expected, result, "Collect function is not collecting all updated values")
    }

    @Test
    fun shouldNotUpdateValueAfterRequestedToClose() {
        val errorHandlerFake = ErrorHandlerFake()
        val manager = basicValueManager(initialValue = 0, errorHandler = errorHandlerFake)
        manager.close()
        manager.update { value ->
            value + 1
        }

        assertEquals(1, errorHandlerFake.exceptions.size, "Missing exception on update value after close manager")
        assertIs<IllegalStateException>(
            errorHandlerFake.exceptions.first(),
            "Update value after closed is not a IllegalStateException"
        )
    }

    @Test
    fun shouldCallErrorHandler_WhenErrorHappens() {
        val random = Random.Default
        val expected = mutableListOf<Throwable>()

        val errorHandlerFake = ErrorHandlerFake()
        val manager = basicValueManager(
            initialValue = 0,
            errorHandler = errorHandlerFake,
        )

        repeat(times = 10) {
            if (random.nextBoolean()) {
                val ex = Exception("Exception number $it")
                expected += ex
                manager.update {
                    throw ex
                }
            }
        }

        assertEquals(0, manager.value, "Value would be not updated when crashing")
        assertEquals(
            expected.size,
            errorHandlerFake.exceptions.size,
            "Missing exceptions on update value crashing always"
        )
        assertContentEquals(expected, errorHandlerFake.exceptions, "Missing exceptions on update value crashing always")
    }

    @Test
    fun shouldCallLifecycleHandler_WhenUpdatingValue() {
        val expected = listOf(
            LifecycleHandlerFake.LifecycleEvent.Before(0, 1),
            LifecycleHandlerFake.LifecycleEvent.After(0, 1),
            LifecycleHandlerFake.LifecycleEvent.Before(1, 2),
            LifecycleHandlerFake.LifecycleEvent.After(1, 2),
            LifecycleHandlerFake.LifecycleEvent.Before(2, 1),
            LifecycleHandlerFake.LifecycleEvent.After(2, 1)
        )

        val lifecycleHandlerFake = LifecycleHandlerFake()
        var value by basicValueManager(
            initialValue = 0,
            onAfterChange = lifecycleHandlerFake,
            onBeforeChange = lifecycleHandlerFake,
        )

        value += 1
        value += 1
        value -= 1

        assertContentEquals(
            expected,
            lifecycleHandlerFake.events,
            "Lifecycle events was ignored in the update value flow"
        )
    }

    @Test
    fun shouldNotUpdateTheValue_WhenHaveInvalidValue() {
        val manager = basicValueManager(0)
        manager.addValidator(object : Validator<Int> {
            override val message: (Int) -> String = { "Value $it should be positive" }

            override fun isValid(value: Int): Boolean = value > 0
        })
        manager.update { value ->
            value - 1
        }
        assertFalse(manager.isValid, "Value should be invalid")
        assertEquals("Value -1 should be positive", manager.messages.first())
        assertEquals(0, manager.value, "Value should be equals to initial value")
    }
}