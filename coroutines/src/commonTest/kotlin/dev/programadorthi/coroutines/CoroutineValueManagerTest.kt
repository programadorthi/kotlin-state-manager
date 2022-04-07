package dev.programadorthi.coroutines

import dev.programadorthi.core.extension.getValue
import dev.programadorthi.core.extension.setValue
import dev.programadorthi.coroutines.extension.coroutineValueManager
import dev.programadorthi.fake.ErrorHandlerFake
import dev.programadorthi.fake.LifecycleHandlerFake
import dev.programadorthi.fake.TransformHandlerFake
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertIs

@OptIn(ExperimentalCoroutinesApi::class)
class CoroutineValueManagerTest {
    private val dispatcher = StandardTestDispatcher()

    @Test
    fun shouldCurrentValueBeEqualsToInitialValue() = runTest(dispatcher) {
        val manager = coroutineValueManager(0, coroutineDispatcher = dispatcher)
        assertEquals(0, manager.value, "Current value is not equals to initial value")
    }

    @Test
    fun shouldCurrentValueBeEqualsToInitialValue_WhenUsingDelegateProperty() = runTest(dispatcher) {
        val value by coroutineValueManager(0, coroutineDispatcher = dispatcher)
        assertEquals(0, value, "Current value is not equals to initial value")
    }

    @Test
    fun shouldChangeCurrentValue_WhenCallUpdate() = runTest(dispatcher) {
        val manager = coroutineValueManager(0, coroutineDispatcher = dispatcher)
        manager.update(manager.value + 1)
        assertEquals(1, manager.value, "Call to update function is not updating current value")
    }

    @Test
    fun shouldChangeCurrentValue_WhenCallUpdateUsingDelegateProperty() = runTest(dispatcher) {
        var value by coroutineValueManager(0, coroutineDispatcher = dispatcher)
        value++ // or value = value + 1
        assertEquals(1, value, "Updating by delegate property is not updating current value")
    }

    @Test
    fun shouldNotInitiateClosed() = runTest(dispatcher) {
        val manager = coroutineValueManager(0, coroutineDispatcher = dispatcher)
        assertEquals(false, manager.closed, "Value manager has started in closed state")
    }

    @Test
    fun shouldCloseAfterRequestedToClose() = runTest(dispatcher) {
        val manager = coroutineValueManager(0, coroutineDispatcher = dispatcher)
        manager.close()
        assertEquals(true, manager.closed, "Value manager still opened after request to close")
    }

    @Test
    fun shouldCollectAllEmittedValue_WhenCollectIsNotSuspend() = runTest(dispatcher) {
        val expected = listOf(1, 2, 3, 4, 5)
        val result = mutableListOf<Int>()

        val manager = coroutineValueManager(0, coroutineDispatcher = dispatcher)
        manager.collect(result::add)
        repeat(times = 5) {
            manager.update(manager.value + 1)
            dispatcher.scheduler.advanceTimeBy(500)
        }

        assertContentEquals(expected, result, "Collect function is not collecting all updated values")
    }

    @Test
    fun shouldCollectAllEmittedValue_WhenCollectIsSuspend() = runTest(dispatcher) {
        val expected = listOf(1, 2, 3, 4, 5)
        val result = mutableListOf<Int>()

        val manager = coroutineValueManager(0, coroutineDispatcher = dispatcher)
        val job = launch {
            manager.toList(result)
        }

        repeat(times = 5) {
            manager.update(manager.value + 1)
            dispatcher.scheduler.advanceTimeBy(500)
        }

        job.cancelAndJoin()

        assertContentEquals(expected, result, "Collect function is not collecting all updated values")
    }

    @Test
    fun shouldNotUpdateValueAfterRequestedToClose() = runTest(dispatcher) {
        val errorHandlerFake = ErrorHandlerFake()
        val manager = coroutineValueManager(
            initialValue = 0,
            errorHandler = errorHandlerFake,
            coroutineDispatcher = dispatcher
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
    fun shouldCallErrorHandler_WhenErrorHappens() = runTest(dispatcher) {
        val random = Random.Default
        val expected = mutableListOf<Throwable>()

        val errorHandlerFake = ErrorHandlerFake()
        val transformHandlerFake = TransformHandlerFake()
        val manager = coroutineValueManager(
            initialValue = 0,
            errorHandler = errorHandlerFake,
            transformHandler = transformHandlerFake,
            coroutineDispatcher = dispatcher
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
        assertEquals(
            expected.size,
            errorHandlerFake.exceptions.size,
            "Missing exceptions on update value crashing always"
        )
        assertContentEquals(expected, errorHandlerFake.exceptions, "Missing exceptions on update value crashing always")
    }

    @Test
    fun shouldCallTransformHandler_WhenHavingACustomUpdateLogic() = runTest(dispatcher) {
        val transformHandlerFake = TransformHandlerFake()
        var value by coroutineValueManager(
            initialValue = 0,
            transformHandler = transformHandlerFake,
            coroutineDispatcher = dispatcher
        )
        transformHandlerFake.transformable = { it * 2 }

        repeat(times = 5) {
            value += it
        }

        assertEquals(52, value, "Value was updated without call custom transform function")
    }

    @Test
    fun shouldCallLifecycleHandler_WhenUpdatingValue() = runTest(dispatcher) {
        val expected = listOf(
            LifecycleHandlerFake.LifecycleEvent.Before(0, 1),
            LifecycleHandlerFake.LifecycleEvent.After(0, 1),
            LifecycleHandlerFake.LifecycleEvent.Before(1, 2),
            LifecycleHandlerFake.LifecycleEvent.After(1, 2),
            LifecycleHandlerFake.LifecycleEvent.Before(2, 1),
            LifecycleHandlerFake.LifecycleEvent.After(2, 1)
        )

        val lifecycleHandlerFake = LifecycleHandlerFake()
        var value by coroutineValueManager(
            initialValue = 0,
            lifecycleHandler = lifecycleHandlerFake,
            coroutineDispatcher = dispatcher
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