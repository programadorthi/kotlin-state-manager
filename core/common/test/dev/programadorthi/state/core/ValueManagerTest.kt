package dev.programadorthi.state.core

import dev.programadorthi.state.core.extension.basicValueManager
import dev.programadorthi.state.core.extension.getValue
import dev.programadorthi.state.core.extension.setValue
import dev.programadorthi.state.core.validation.Validator
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertTrue

@OptIn(ExperimentalStdlibApi::class)
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

        assertContentEquals(
            expected,
            result,
            "Collect function is not collecting all updated values"
        )
    }

    @Test
    fun shouldNotUpdateValueAfterRequestedToClose() {
        val manager = basicValueManager(initialValue = 0)
        manager.close()

        val exception = assertFails {
            manager.update { value ->
                value + 1
            }
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

    @Test
    fun shouldUpdateValueAndClose() {
        val manager = basicValueManager(initialValue = 0)

        manager.use {
            it.update { value ->
                value + 1
            }
        }

        assertEquals(1, manager.value, "Value should be updated before close")
        assertTrue(manager.closed, "Manager should be closed")
    }

    @Test
    fun shouldCallErrorHandler_WhenErrorHappens() {
        val random = Random.Default
        val expected = mutableListOf<Throwable>()
        val exceptions = mutableListOf<Throwable>()
        val manager = basicValueManager(
            initialValue = 0,
            errorHandler = {
                exceptions.add(it)
            },
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
            exceptions.size,
            "Missing exceptions on update value crashing always"
        )
        assertContentEquals(
            expected,
            exceptions,
            "Missing exceptions on update value crashing always"
        )
    }

    @Test
    fun shouldCallLifecycleHandler_WhenUpdatingValue() {
        val expected = listOf(
            0 to 1,
            1 to 2,
            2 to 1,
        )
        val events = mutableListOf<Pair<Int, Int>>()

        var value by basicValueManager(
            initialValue = 0,
            changeHandler = { previous, next ->
                events += previous to next
            },
        )

        value += 1
        value += 1
        value -= 1

        assertContentEquals(
            expected,
            events,
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
        assertFalse(manager.isValid, "Value {${manager.value}} should be invalid")
        assertEquals("Value -1 should be positive", manager.messages.first())
        assertEquals(0, manager.value, "Value should be equals to initial value")
    }

    @Test
    fun shouldSerializeAndDeserialize() {
        val data = SerializableValueManager(
            intValue = basicValueManager(1),
            stringValue = basicValueManager("valueManager"),
            color = basicValueManager(Color("red")),
            composite = basicValueManager(
                basicValueManager(listOf(Color("blue"), Color("green")))
            )
        )
        val json = Json.encodeToString(data)
        println(json)
        val decoded = Json.decodeFromString<SerializableValueManager>(json)

        assertEquals(
            """{"intValue":1,"stringValue":"valueManager","color":{"name":"red"},"composite":[{"name":"blue"},{"name":"green"}]}""",
            json
        )
        assertEquals(data, decoded)
    }
}