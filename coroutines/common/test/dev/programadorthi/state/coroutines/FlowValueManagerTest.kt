package dev.programadorthi.state.coroutines

import dev.programadorthi.state.core.extension.getValue
import dev.programadorthi.state.core.extension.setValue
import dev.programadorthi.state.coroutines.extension.flowValueManager
import dev.programadorthi.state.coroutines.fake.ChangeHandlerFake
import dev.programadorthi.state.coroutines.fake.ErrorHandlerFake
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
        val manager = flowValueManager(0)
        assertEquals(0, manager.value, "Current value is not equals to initial value")
    }

    @Test
    fun shouldCurrentValueBeEqualsToInitialValue_WhenUsingDelegateProperty() = runTest {
        val value by flowValueManager(0)
        assertEquals(0, value, "Current value is not equals to initial value")
    }

    @Test
    fun shouldChangeCurrentValue_WhenCallUpdate() = runTest {
        val manager = flowValueManager(0)
        manager.update { value -> value + 1 }
        assertEquals(1, manager.value, "Call to update function is not updating current value")
    }

    @Test
    fun shouldChangeCurrentValue_WhenCallUpdateUsingDelegateProperty() = runTest {
        var value by flowValueManager(0)
        value++ // or value = value + 1
        assertEquals(1, value, "Updating by delegate property is not updating current value")
    }

    @Test
    fun shouldNotInitiateClosed() = runTest {
        val manager = flowValueManager(0)
        assertEquals(false, manager.closed, "Value manager has started in closed state")
    }

    @Test
    fun shouldCloseAfterRequestedToClose() = runTest {
        val manager = flowValueManager(0)
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

        assertContentEquals(
            expected,
            result,
            "Collect function is not collecting all updated values"
        )
    }

    @Test
    fun shouldCollectAllEmittedValue_WhenCollectIsSuspend() = runTest {
        val expected = listOf(1, 2, 3, 4, 5)
        val result = mutableListOf<Int>()

        val manager = flowValueManager(0)
        val job = launch {
            manager.toList(result)
        }

        repeat(times = 5) {
            manager.update { value -> value + 1 }
            advanceTimeBy(500)
        }

        job.cancelAndJoin()

        assertContentEquals(
            expected,
            result,
            "Collect function is not collecting all updated values"
        )
    }

    @Test
    fun shouldNotUpdateValueAfterRequestedToClose() = runTest {
        val errorHandlerFake = ErrorHandlerFake()
        val manager = flowValueManager(
            initialValue = 0,
            errorHandler = errorHandlerFake,
        )
        manager.close()
        manager.update { value -> value + 1 }

        assertEquals(
            1,
            errorHandlerFake.exceptions.size,
            "Missing exception on update value after close manager"
        )
        assertIs<IllegalStateException>(
            errorHandlerFake.exceptions.first(),
            "Update value after closed is not a IllegalStateException"
        )
    }

    @Test
    fun shouldCallErrorHandler_WhenErrorHappens() = runTest {
        val random = Random.Default
        val expected = mutableListOf<Throwable>()

        val errorHandlerFake = ErrorHandlerFake()
        val manager = flowValueManager(
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
        assertContentEquals(
            expected,
            errorHandlerFake.exceptions,
            "Missing exceptions on update value crashing always"
        )
    }

    @Test
    fun shouldCallLifecycleHandler_WhenUpdatingValue() = runTest {
        val expected = listOf(
            0 to 1,
            1 to 2,
            2 to 1,
        )

        val changeHandler = ChangeHandlerFake()
        var value by flowValueManager(
            initialValue = 0,
            changeHandler = changeHandler,
        )

        value += 1
        value += 1
        value -= 1

        assertContentEquals(
            expected,
            changeHandler.events,
            "Lifecycle events was ignored in the update value flow"
        )
    }
}