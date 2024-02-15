package dev.programadorthi.state.core

import dev.programadorthi.state.core.extension.basicValueManager
import dev.programadorthi.state.core.extension.getValue
import dev.programadorthi.state.core.extension.setValue
import dev.programadorthi.state.core.fake.ErrorHandlerFake
import dev.programadorthi.state.core.fake.LifecycleHandlerFake
import dev.programadorthi.state.core.fake.TransformHandlerFake
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
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
        manager.update(manager.value + 1)
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
            manager.update(manager.value + 1)
        }

        assertContentEquals(expected, result, "Collect function is not collecting all updated values")
    }

    @Test
    fun shouldNotUpdateValueAfterRequestedToClose() {
        val errorHandlerFake = ErrorHandlerFake()
        val manager = basicValueManager(initialValue = 0, errorHandler = errorHandlerFake)
        manager.close()
        manager.update(manager.value + 1)

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
        val transformHandlerFake = TransformHandlerFake()
        val manager = basicValueManager(
            initialValue = 0,
            errorHandler = errorHandlerFake,
            transformHandler = transformHandlerFake
        )

        repeat(times = 10) {
            if (random.nextBoolean()) {
                val ex = Exception("Exception number $it")
                expected += ex
                transformHandlerFake.breakable = ex
                manager.update(manager.value + 1)
            }
        }

        assertEquals(0, manager.value, "Value would be not updated when crashing")
        assertEquals(expected.size, errorHandlerFake.exceptions.size, "Missing exceptions on update value crashing always")
        assertContentEquals(expected, errorHandlerFake.exceptions, "Missing exceptions on update value crashing always")
    }

    @Test
    fun shouldCallTransformHandler_WhenHavingACustomUpdateLogic() {
        val transformHandlerFake = TransformHandlerFake()
        var value by basicValueManager(
            initialValue = 0,
            transformHandler = transformHandlerFake
        )
        transformHandlerFake.transformable = { it * 2 }

        repeat(times = 5) {
            value += it
        }

        assertEquals(52, value, "Value was updated without call custom transform function")
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

        assertContentEquals(expected, lifecycleHandlerFake.events, "Lifecycle events was ignored in the update value flow")
    }
}