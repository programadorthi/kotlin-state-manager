package dev.programadorthi.state.validator

import dev.programadorthi.state.core.extension.basicValueManager
import dev.programadorthi.state.core.extension.plusAssign
import dev.programadorthi.state.validator.any.InValidator
import dev.programadorthi.state.validator.any.IsEqualToValidator
import dev.programadorthi.state.validator.any.IsNotEqualToValidator
import dev.programadorthi.state.validator.any.IsNotNullValidator
import dev.programadorthi.state.validator.any.IsNullValidator
import dev.programadorthi.state.validator.any.NotInValidator
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class AnyValidatorsTest {

    @Test
    fun shouldBeValidWithInValidator() {
        // GIVEN
        val manager = basicValueManager(0)
        manager += InValidator(
            values = listOf(1, 2, 3, 4, 5),
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { it + 3 }

        // THEN
        assertEquals(3, manager.value)
        assertTrue(manager.isValid, "should be valid using in validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid in validator")
    }

    @Test
    fun shouldBeInvalidWithInValidator() {
        // GIVEN
        val manager = basicValueManager(0)
        manager += InValidator(
            values = listOf(1, 2, 3, 4, 5),
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { it + 6 }

        // THEN
        assertEquals(0, manager.value)
        assertFalse(manager.isValid, "should be invalid using in validator")
        assertEquals("6 is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithIsEqualToValidator() {
        // GIVEN
        val manager = basicValueManager(0)
        manager += IsEqualToValidator(
            other = 4,
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { it + 4 }

        // THEN
        assertEquals(4, manager.value)
        assertTrue(manager.isValid, "should be valid using is equal to validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid is equal to validator")
    }

    @Test
    fun shouldBeInvalidWithIsEqualToValidator() {
        // GIVEN
        val manager = basicValueManager(0)
        manager += IsEqualToValidator(
            other = 7,
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { it + 2 }

        // THEN
        assertEquals(0, manager.value)
        assertFalse(manager.isValid, "should be invalid using is equal to validator")
        assertEquals("2 is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithIsNotEqualToValidator() {
        // GIVEN
        val manager = basicValueManager(0)
        manager += IsNotEqualToValidator(
            other = 1,
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { it + 3 }

        // THEN
        assertEquals(3, manager.value)
        assertTrue(manager.isValid, "should be valid using is not equal to validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid is not equal to validator")
    }

    @Test
    fun shouldBeInvalidWithIsNotEqualToValidator() {
        // GIVEN
        val manager = basicValueManager(0)
        manager += IsNotEqualToValidator(
            other = 9,
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { it + 9 }

        // THEN
        assertEquals(0, manager.value)
        assertFalse(manager.isValid, "should be invalid using is not equal to validator")
        assertEquals("9 is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithIsNotNullValidator() {
        // GIVEN
        val manager = basicValueManager<Int?>(0)
        manager += IsNotNullValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { 5 }

        // THEN
        assertEquals(5, manager.value)
        assertTrue(manager.isValid, "should be valid using is not null validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid is not null validator")
    }

    @Test
    fun shouldBeInvalidWithIsNotNullValidator() {
        // GIVEN
        val manager = basicValueManager<Int?>(0)
        manager += IsNotNullValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { null }

        // THEN
        assertEquals(0, manager.value)
        assertFalse(manager.isValid, "should be invalid using is not null validator")
        assertEquals("null is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithIsNullValidator() {
        // GIVEN
        val manager = basicValueManager<Int?>(0)
        manager += IsNullValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { null }

        // THEN
        assertEquals(null, manager.value)
        assertTrue(manager.isValid, "should be valid using is null validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid is null validator")
    }

    @Test
    fun shouldBeInvalidWithIsNullValidator() {
        // GIVEN
        val manager = basicValueManager<Int?>(0)
        manager += IsNullValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { 1 }

        // THEN
        assertEquals(0, manager.value)
        assertFalse(manager.isValid, "should be invalid using is null validator")
        assertEquals("1 is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithNotInValidator() {
        // GIVEN
        val manager = basicValueManager<Int?>(0)
        manager += NotInValidator(
            values = listOf(1, 2, 3, 4, 5),
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { 9 }

        // THEN
        assertEquals(9, manager.value)
        assertTrue(manager.isValid, "should be valid using not in validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid not in validator")
    }

    @Test
    fun shouldBeInvalidWithNotInValidator() {
        // GIVEN
        val manager = basicValueManager(0)
        manager += NotInValidator(
            values = listOf(1, 2, 3, 4, 5),
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { 2 }

        // THEN
        assertEquals(0, manager.value)
        assertFalse(manager.isValid, "should be invalid using not in validator")
        assertEquals("2 is invalid", manager.messages.first())
    }
}