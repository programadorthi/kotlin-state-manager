package dev.programadorthi.state.coroutines

import dev.programadorthi.state.core.extension.getValue
import dev.programadorthi.state.core.extension.setValue
import dev.programadorthi.state.coroutines.extension.flowValueManager
import dev.programadorthi.state.coroutines.fake.ErrorHandlerFake
import dev.programadorthi.state.coroutines.fake.LifecycleHandlerFake
import dev.programadorthi.state.coroutines.fake.TransformHandlerFake
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertIs

@OptIn(ExperimentalCoroutinesApi::class)
class FlowValueManagerTest {

    @Test
    fun shouldCurrentValueBeEqualsToInitialValue() = runTest {
        val context = coroutineContext + Job()
        val manager = flowValueManager(0, coroutineContext = context)
        assertEquals(0, manager.value, "Current value is not equals to initial value")
    }

    @Test
    fun shouldCurrentValueBeEqualsToInitialValue_WhenUsingDelegateProperty() = runTest {
        val context = coroutineContext + Job()
        val value by flowValueManager(0, coroutineContext = context)
        assertEquals(0, value, "Current value is not equals to initial value")
    }

    @Test
    fun shouldChangeCurrentValue_WhenCallUpdate() = runTest {
        val context = coroutineContext + Job()
        val manager = flowValueManager(0, coroutineContext = context)
        manager.update { value -> value + 1 }
        assertEquals(1, manager.value, "Call to update function is not updating current value")
    }

    @Test
    fun shouldChangeCurrentValue_WhenCallUpdateUsingDelegateProperty() = runTest {
        val context = coroutineContext + Job()
        var value by flowValueManager(0, coroutineContext = context)
        value++ // or value = value + 1
        assertEquals(1, value, "Updating by delegate property is not updating current value")
    }

    @Test
    fun shouldNotInitiateClosed() = runTest {
        val context = coroutineContext + Job()
        val manager = flowValueManager(0, coroutineContext = context)
        assertEquals(false, manager.closed, "Value manager has started in closed state")
    }

    @Test
    fun shouldCloseAfterRequestedToClose() = runTest {
        val context = coroutineContext + Job()
        val manager = flowValueManager(0, coroutineContext = context)
        manager.close()
        assertEquals(true, manager.closed, "Value manager still opened after request to close")
    }

    @Test
    fun shouldCollectAllEmittedValue_WhenCollectIsNotSuspend() = runTest {
        val context = coroutineContext + Job()
        val expected = listOf(1, 2, 3, 4, 5)
        val result = mutableListOf<Int>()

        val manager = flowValueManager(0, coroutineContext = context)
        manager.collect(result::add)
        repeat(times = 5) {
            manager.update { value -> value + 1 }
            advanceTimeBy(500)
        }

        assertContentEquals(expected, result, "Collect function is not collecting all updated values")
    }

    @Test
    fun shouldCollectAllEmittedValue_WhenCollectIsSuspend() = runTest {
        val context = coroutineContext + Job()
        val expected = listOf(1, 2, 3, 4, 5)
        val result = mutableListOf<Int>()

        val manager = flowValueManager(0, coroutineContext = context)
        val job = launch {
            manager.toList(result)
        }

        repeat(times = 5) {
            manager.update { value -> value + 1 }
            advanceTimeBy(500)
        }

        job.cancelAndJoin()

        assertContentEquals(expected, result, "Collect function is not collecting all updated values")
    }

    @Test
    fun shouldNotUpdateValueAfterRequestedToClose() = runTest {
        val context = coroutineContext + Job()
        val errorHandlerFake = ErrorHandlerFake()
        val manager = flowValueManager(
            initialValue = 0,
            errorHandler = errorHandlerFake,
            coroutineContext = context
        )
        manager.close()
        manager.update { value -> value + 1 }

        assertEquals(1, errorHandlerFake.exceptions.size, "Missing exception on update value after close manager")
        assertIs<IllegalStateException>(
            errorHandlerFake.exceptions.first(),
            "Update value after closed is not a IllegalStateException"
        )
    }

    @Test
    fun shouldCallErrorHandler_WhenErrorHappens() = runTest {
        val context = coroutineContext + Job()
        val random = Random.Default
        val expected = mutableListOf<Throwable>()

        val errorHandlerFake = ErrorHandlerFake()
        val transformHandlerFake = TransformHandlerFake()
        val manager = flowValueManager(
            initialValue = 0,
            errorHandler = errorHandlerFake,
            transformHandler = transformHandlerFake,
            coroutineContext = context
        )

        repeat(times = 10) {
            if (random.nextBoolean()) {
                val ex = Exception("Exception number $it")
                expected += ex
                transformHandlerFake.breakable = ex
                manager.update { value -> value + 1 }
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
    fun shouldCallTransformHandler_WhenHavingACustomUpdateLogic() = runTest {
        val context = coroutineContext + Job()
        val transformHandlerFake = TransformHandlerFake()
        var value by flowValueManager(
            initialValue = 0,
            transformHandler = transformHandlerFake,
            coroutineContext = context
        )
        transformHandlerFake.transformable = { it * 2 }

        repeat(times = 5) {
            value += it
        }

        assertEquals(52, value, "Value was updated without call custom transform function")
    }

    @Test
    fun shouldCallLifecycleHandler_WhenUpdatingValue() = runTest {
        val context = coroutineContext + Job()
        val expected = listOf(
            LifecycleHandlerFake.LifecycleEvent.Before(0, 1),
            LifecycleHandlerFake.LifecycleEvent.After(0, 1),
            LifecycleHandlerFake.LifecycleEvent.Before(1, 2),
            LifecycleHandlerFake.LifecycleEvent.After(1, 2),
            LifecycleHandlerFake.LifecycleEvent.Before(2, 1),
            LifecycleHandlerFake.LifecycleEvent.After(2, 1)
        )

        val lifecycleHandlerFake = LifecycleHandlerFake()
        var value by flowValueManager(
            initialValue = 0,
            onAfterChange = lifecycleHandlerFake,
            onBeforeChange = lifecycleHandlerFake,
            coroutineContext = context
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