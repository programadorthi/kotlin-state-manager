package dev.programadorthi.compose

import dev.programadorthi.compose.extension.composeValueManager
import dev.programadorthi.core.extension.getValue
import dev.programadorthi.core.extension.setValue
import dev.programadorthi.fake.ErrorHandlerFake
import dev.programadorthi.fake.LifecycleHandlerFake
import dev.programadorthi.fake.TransformHandlerFake
import dev.programadorthi.fake.compositionTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertIs

@OptIn(ExperimentalCoroutinesApi::class)
class ComposeBasicValueManagerTest {
    @Test
    fun shouldCurrentValueBeEqualsToInitialValue() = compositionTest {
        val expected = 0
        val manager = composeValueManager(initialValue = 0)
        assertEquals(expected, manager.value, "Current value is not equals to initial value")
    }

    @Test
    fun shouldCurrentValueBeEqualsToInitialValue_WhenUsingDelegateProperty() = compositionTest {
        val expected = 0
        val value by composeValueManager(initialValue = 0)
        assertEquals(expected, value, "Current value is not equals to initial value")
    }

    @Test
    fun shouldChangeCurrentValue_WhenCallUpdate() = compositionTest {
        val expected = listOf(0, 1)
        val result = mutableListOf<Int>()
        val manager = composeValueManager(initialValue = 0)

        compose {
            // Using recomposition to get next values
            result += manager.value
        }

        manager.update(manager.value + 1)
        advance() // recompose

        assertEquals(expected, result, "Call to update function is not updating current value")
    }

    @Test
    fun shouldChangeCurrentValue_WhenCallUpdateUsingDelegateProperty() = compositionTest {
        val expected = listOf(0, 1)
        val result = mutableListOf<Int>()
        var value by composeValueManager(initialValue = 0)

        compose {
            // Using recomposition to get next values
            result += value
        }

        value++ // or value = value + 1
        advance() // recompose

        assertEquals(expected, result, "Updating by delegate property is not updating current value")
    }

    @Test
    fun shouldNotInitiateClosed() = compositionTest {
        val manager = composeValueManager(initialValue = 0)
        assertEquals(false, manager.closed, "Value manager has started in closed state")
    }

    @Test
    fun shouldCloseAfterRequestedToClose() = compositionTest {
        val manager = composeValueManager(initialValue = 0)
        manager.close()
        assertEquals(true, manager.closed, "Value manager still opened after request to close")
    }

    @Test
    fun shouldCollectAllEmittedValue_WhenCollectIsNotSuspend() = compositionTest {
        val expected = listOf(0, 1, 2, 3, 4)
        val result = mutableListOf<Int>()
        val manager = composeValueManager(initialValue = 0)

        manager.collect(result::add)

        repeat(times = 4) {
            manager.update(manager.value + 1)
        }

        assertContentEquals(expected, result, "Collect function is not collecting all updated values")
    }

    @Test
    fun shouldCollectAllEmittedValue_WhenCollectIsSuspend() = compositionTest {
        val expected = listOf(0, 1, 2, 3, 4)
        val result = mutableListOf<Int>()
        val manager = composeValueManager(initialValue = 0)

        compose {
            // Using recomposition to get next values
            result += manager.value
        }

        repeat(times = 4) {
            manager.update(manager.value + 1)
            advanceCount()
        }

        assertContentEquals(expected, result, "Collect function is not collecting all updated values")
    }

    @Test
    fun shouldNotUpdateValueAfterRequestedToClose() = compositionTest {
        val errorHandlerFake = ErrorHandlerFake()
        val manager = composeValueManager(
            initialValue = 0,
            errorHandler = errorHandlerFake
        )
        manager.close()
        manager.update(manager.value + 1)

        assertEquals(1, errorHandlerFake.exceptions.size, "Missing exception on update value after close manager")
        assertIs<IllegalStateException>(
            errorHandlerFake.exceptions.first(),
            "Update value after closed is not a IllegalStateException"
        )
    }

    @Test
    fun shouldCallErrorHandler_WhenErrorHappens() = compositionTest {
        val random = Random.Default
        val results = mutableListOf<Int>()
        val expected = mutableListOf<Throwable>()

        val errorHandlerFake = ErrorHandlerFake()
        val transformHandlerFake = TransformHandlerFake()
        val manager = composeValueManager(
            initialValue = 0,
            errorHandler = errorHandlerFake,
            transformHandler = transformHandlerFake
        )

        compose {
            results += manager.value
        }

        repeat(times = 10) {
            if (random.nextBoolean()) {
                val ex = Exception("Exception number $it")
                expected += ex
                transformHandlerFake.breakable = ex
                manager.update(manager.value + 1)
                advance()
            }
        }

        assertContentEquals(listOf(0), results, "Value would be not updated when crashing")
        assertEquals(
            expected.size,
            errorHandlerFake.exceptions.size,
            "Missing exceptions on update value crashing always"
        )
        assertContentEquals(expected, errorHandlerFake.exceptions, "Missing exceptions on update value crashing always")
    }

    @Test
    fun shouldCallTransformHandler_WhenHavingACustomUpdateLogic() = compositionTest {
        val transformHandlerFake = TransformHandlerFake()
        var value by composeValueManager(
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
    fun shouldCallLifecycleHandler_WhenUpdatingValue() = compositionTest {
        val expected = listOf(
            LifecycleHandlerFake.LifecycleEvent.Before(0, 1),
            LifecycleHandlerFake.LifecycleEvent.After(0, 1),
            LifecycleHandlerFake.LifecycleEvent.Before(1, 2),
            LifecycleHandlerFake.LifecycleEvent.After(1, 2),
            LifecycleHandlerFake.LifecycleEvent.Before(2, 1),
            LifecycleHandlerFake.LifecycleEvent.After(2, 1)
        )

        val lifecycleHandlerFake = LifecycleHandlerFake()
        var value by composeValueManager(
            initialValue = 0,
            lifecycleHandler = lifecycleHandlerFake
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
}
