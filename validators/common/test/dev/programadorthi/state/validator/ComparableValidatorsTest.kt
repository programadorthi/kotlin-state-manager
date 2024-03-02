package dev.programadorthi.state.validator

import dev.programadorthi.state.core.extension.basicValueManager
import dev.programadorthi.state.core.extension.plusAssign
import dev.programadorthi.state.validator.comparable.InValidator
import dev.programadorthi.state.validator.comparable.IsGreaterThanOrEqualToValidator
import dev.programadorthi.state.validator.comparable.IsGreaterThanValidator
import dev.programadorthi.state.validator.comparable.IsLessThanOrEqualToValidator
import dev.programadorthi.state.validator.comparable.IsLessThanValidator
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ComparableValidatorsTest {

    @Test
    fun shouldBeValidWithInValidator() {
        // GIVEN
        val manager = basicValueManager(0)
        manager += InValidator(
            start = 5,
            end = 16,
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { 13 }

        // THEN
        assertEquals(13, manager.value)
        assertTrue(manager.isValid, "should be valid using in validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid in validator")
    }

    @Test
    fun shouldBeInvalidWithInValidator() {
        // GIVEN
        val manager = basicValueManager(0)
        manager += InValidator(
            start = 5,
            end = 16,
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { 44 }

        // THEN
        assertEquals(0, manager.value)
        assertFalse(manager.isValid, "should be invalid using in validator")
        assertEquals("44 is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithIsGreaterThanOrEqualToValidator() {
        // GIVEN
        val manager = basicValueManager(0)
        manager += IsGreaterThanOrEqualToValidator(
            start = 5,
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { 13 }

        // THEN
        assertEquals(13, manager.value)
        assertTrue(manager.isValid, "should be valid using is greater than or equal to validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid is greater than or equal to validator")
    }

    @Test
    fun shouldBeInvalidWithIsGreaterThanOrEqualToValidator() {
        // GIVEN
        val manager = basicValueManager(0)
        manager += IsGreaterThanOrEqualToValidator(
            start = 15,
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { 4 }

        // THEN
        assertEquals(0, manager.value)
        assertFalse(manager.isValid, "should be invalid using is greater than or equal to validator")
        assertEquals("4 is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithIsGreaterThanValidator() {
        // GIVEN
        val manager = basicValueManager(0)
        manager += IsGreaterThanValidator(
            start = 5,
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { 13 }

        // THEN
        assertEquals(13, manager.value)
        assertTrue(manager.isValid, "should be valid using is greater than validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid is greater than validator")
    }

    @Test
    fun shouldBeInvalidWithIsGreaterThanValidator() {
        // GIVEN
        val manager = basicValueManager(0)
        manager += IsGreaterThanValidator(
            start = 5,
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { 4 }

        // THEN
        assertEquals(0, manager.value)
        assertFalse(manager.isValid, "should be invalid using is greater than validator")
        assertEquals("4 is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithIsLessThanOrEqualToValidator() {
        // GIVEN
        val manager = basicValueManager(0)
        manager += IsLessThanOrEqualToValidator(
            end = 55,
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { 13 }

        // THEN
        assertEquals(13, manager.value)
        assertTrue(manager.isValid, "should be valid using is less than or equal to validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid is less than or equal to validator")
    }

    @Test
    fun shouldBeInvalidWithIsLessThanOrEqualToValidator() {
        // GIVEN
        val manager = basicValueManager(0)
        manager += IsLessThanOrEqualToValidator(
            end = 15,
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { 44 }

        // THEN
        assertEquals(0, manager.value)
        assertFalse(manager.isValid, "should be invalid using is less than or equal to validator")
        assertEquals("44 is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithIsLessThanValidator() {
        // GIVEN
        val manager = basicValueManager(0)
        manager += IsLessThanValidator(
            end = 55,
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { 13 }

        // THEN
        assertEquals(13, manager.value)
        assertTrue(manager.isValid, "should be valid using is less than validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid is less than validator")
    }

    @Test
    fun shouldBeInvalidWithIsLessThanValidator() {
        // GIVEN
        val manager = basicValueManager(0)
        manager += IsLessThanValidator(
            end = 5,
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { 44 }

        // THEN
        assertEquals(0, manager.value)
        assertFalse(manager.isValid, "should be invalid using is less than validator")
        assertEquals("44 is invalid", manager.messages.first())
    }
}