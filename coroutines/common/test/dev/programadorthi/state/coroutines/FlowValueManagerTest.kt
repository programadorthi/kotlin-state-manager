package dev.programadorthi.state.coroutines

import dev.programadorthi.state.core.extension.basicValueManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertIs

@OptIn(ExperimentalCoroutinesApi::class)
class FlowValueManagerTest {

    @Test
    fun shouldCurrentValueBeEqualsToInitialValue() = runTest {
        val manager = basicValueManager(0).asMutableStateFlow()
        assertEquals(0, manager.value, "Current value is not equals to initial value")
    }

    @Test
    fun shouldCurrentValueBeEqualsToInitialValue_WhenUsingDelegateProperty() = runTest {
        val value by basicValueManager(0).asMutableStateFlow()
        assertEquals(0, value, "Current value is not equals to initial value")
    }

    @Test
    fun shouldChangeCurrentValue_WhenCallUpdate() = runTest {
        val manager = basicValueManager(0).asMutableStateFlow()
        manager.value += 1
        assertEquals(1, manager.value, "Call to update function is not updating current value")
    }

    @Test
    fun shouldChangeCurrentValue_WhenCallUpdateUsingDelegateProperty() = runTest {
        var value by basicValueManager(0).asMutableStateFlow()
        value++ // or value = value + 1
        assertEquals(1, value, "Updating by delegate property is not updating current value")
    }

    @Test
    fun shouldCollectAllEmittedValue_WhenCollectIsNotSuspend() = runTest {
        val expected = listOf(1, 2, 3, 4, 5)
        val result = mutableListOf<Int>()

        val manager = basicValueManager(0).asMutableStateFlow()
        val job = launch(coroutineContext + Job()) {
            manager.collect(result::add)
        }
        repeat(times = 5) {
            manager.value += 1
            advanceTimeBy(500)
        }

        assertContentEquals(
            expected,
            result,
            "Collect function is not collecting all updated values"
        )
        job.cancelAndJoin()
    }

    @Test
    fun shouldCollectAllEmittedValue_WhenCollectIsSuspend() = runTest {
        val expected = listOf(1, 2, 3, 4, 5)
        val result = mutableListOf<Int>()

        val manager = basicValueManager(0).asMutableStateFlow()
        val job = launch {
            manager.toList(result)
        }

        repeat(times = 5) {
            manager.value += 1
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
        val manager = basicValueManager(0)
        manager.close()
        val state = manager.asMutableStateFlow()

        val exception = assertFails {
            state.value += 1
        }

        assertIs<IllegalStateException>(
            exception,
            "Update value after closed is not a IllegalStateException"
        )
        assertEquals(
            "Manager is closed and can't update the value",
            exception.message,
            "Missing exception on update value after close manager"
        )
    }

}