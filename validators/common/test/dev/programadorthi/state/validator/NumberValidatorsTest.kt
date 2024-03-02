package dev.programadorthi.state.validator

import dev.programadorthi.state.core.extension.basicValueManager
import dev.programadorthi.state.core.extension.plusAssign
import dev.programadorthi.state.validator.number.IsFiniteValidator
import dev.programadorthi.state.validator.number.IsInfiniteValidator
import dev.programadorthi.state.validator.number.IsNaNValidator
import dev.programadorthi.state.validator.number.IsNegativeValidator
import dev.programadorthi.state.validator.number.IsNotOneValidator
import dev.programadorthi.state.validator.number.IsNotZeroValidator
import dev.programadorthi.state.validator.number.IsOneValidator
import dev.programadorthi.state.validator.number.IsPositiveValidator
import dev.programadorthi.state.validator.number.IsZeroValidator
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class NumberValidatorsTest {

    @Test
    fun shouldBeValidWithIsFiniteValidator() {
        // GIVEN
        val manager = basicValueManager(0.0)
        manager += IsFiniteValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { Double.MAX_VALUE }

        // THEN
        assertEquals(Double.MAX_VALUE, manager.value)
        assertTrue(manager.isValid, "should be valid using is finite validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid is finite validator")
    }

    @Test
    fun shouldBeInvalidWithIsFiniteValidator() {
        // GIVEN
        val manager = basicValueManager(0.0)
        manager += IsFiniteValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { Double.POSITIVE_INFINITY }

        // THEN
        assertEquals(0.0, manager.value)
        assertFalse(manager.isValid, "should be invalid using is finite validator")
        assertEquals("${Double.POSITIVE_INFINITY} is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithIsInfiniteValidator() {
        // GIVEN
        val manager = basicValueManager(0.0)
        manager += IsInfiniteValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { Double.POSITIVE_INFINITY }

        // THEN
        assertEquals(Double.POSITIVE_INFINITY, manager.value)
        assertTrue(manager.isValid, "should be valid using is infinite validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid is infinite validator")
    }

    @Test
    fun shouldBeInvalidWithIsInfiniteValidator() {
        // GIVEN
        val manager = basicValueManager(0.0)
        manager += IsInfiniteValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { Double.MAX_VALUE }

        // THEN
        assertEquals(0.0, manager.value)
        assertFalse(manager.isValid, "should be invalid using is infinite validator")
        assertEquals("${Double.MAX_VALUE} is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithIsNaNValidator() {
        // GIVEN
        val manager = basicValueManager(0.0)
        manager += IsNaNValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { Double.NaN }

        // THEN
        assertEquals(Double.NaN, manager.value)
        assertTrue(manager.isValid, "should be valid using is NaN validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid is NaN validator")
    }

    @Test
    fun shouldBeInvalidWithIsNaNValidator() {
        // GIVEN
        val manager = basicValueManager(0.0)
        manager += IsNaNValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { 45.88 }

        // THEN
        assertEquals(0.0, manager.value)
        assertFalse(manager.isValid, "should be invalid using is NaN validator")
        assertEquals("45.88 is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithIsNegativeValidator() {
        // GIVEN
        val manager = basicValueManager(0.0)
        manager += IsNegativeValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { -12.567 }

        // THEN
        assertEquals(-12.567, manager.value)
        assertTrue(manager.isValid, "should be valid using is negative validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid is negative validator")
    }

    @Test
    fun shouldBeInvalidWithIsNegativeValidator() {
        // GIVEN
        val manager = basicValueManager(0.0)
        manager += IsNegativeValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { 12.567 }

        // THEN
        assertEquals(0.0, manager.value)
        assertFalse(manager.isValid, "should be invalid using is negative validator")
        assertEquals("12.567 is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithIsNotOneValidator() {
        // GIVEN
        val manager = basicValueManager(0.0)
        manager += IsNotOneValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { 7.8 }

        // THEN
        assertEquals(7.8, manager.value)
        assertTrue(manager.isValid, "should be valid using is not one validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid is not one validator")
    }

    @Test
    fun shouldBeInvalidWithIsNotOneValidator() {
        // GIVEN
        val manager = basicValueManager(0.0)
        manager += IsNotOneValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { 1.0 }

        // THEN
        assertEquals(0.0, manager.value)
        assertFalse(manager.isValid, "should be invalid using is not one validator")
        assertEquals("1.0 is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithIsNotZeroValidator() {
        // GIVEN
        val manager = basicValueManager(0.0)
        manager += IsNotZeroValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { 1.0 }

        // THEN
        assertEquals(1.0, manager.value)
        assertTrue(manager.isValid, "should be valid using is not zero validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid is not zero validator")
    }

    @Test
    fun shouldBeInvalidWithIsNotZeroValidator() {
        // GIVEN
        val manager = basicValueManager(1.0)
        manager += IsNotZeroValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { 0.0 }

        // THEN
        assertEquals(1.0, manager.value)
        assertFalse(manager.isValid, "should be invalid using is not zero validator")
        assertEquals("0.0 is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithIsOneValidator() {
        // GIVEN
        val manager = basicValueManager(0.0)
        manager += IsOneValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { 1.0 }

        // THEN
        assertEquals(1.0, manager.value)
        assertTrue(manager.isValid, "should be valid using is one validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid is one validator")
    }

    @Test
    fun shouldBeInvalidWithIsOneValidator() {
        // GIVEN
        val manager = basicValueManager(0.0)
        manager += IsOneValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { 10.0 }

        // THEN
        assertEquals(0.0, manager.value)
        assertFalse(manager.isValid, "should be invalid using is one validator")
        assertEquals("10.0 is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithIsPositiveValidator() {
        // GIVEN
        val manager = basicValueManager(0.0)
        manager += IsPositiveValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { 1.0 }

        // THEN
        assertEquals(1.0, manager.value)
        assertTrue(manager.isValid, "should be valid using is positive validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid is positive validator")
    }

    @Test
    fun shouldBeInvalidWithIsPositiveValidator() {
        // GIVEN
        val manager = basicValueManager(0.0)
        manager += IsPositiveValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { -10.0 }

        // THEN
        assertEquals(0.0, manager.value)
        assertFalse(manager.isValid, "should be invalid using is positive validator")
        assertEquals("-10.0 is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithIsZeroValidator() {
        // GIVEN
        val manager = basicValueManager(10.0)
        manager += IsZeroValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { 0.0 }

        // THEN
        assertEquals(0.0, manager.value)
        assertTrue(manager.isValid, "should be valid using is zero validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid is zero validator")
    }

    @Test
    fun shouldBeInvalidWithIsZeroValidator() {
        // GIVEN
        val manager = basicValueManager(0.0)
        manager += IsZeroValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { 10.0 }

        // THEN
        assertEquals(0.0, manager.value)
        assertFalse(manager.isValid, "should be invalid using is zero validator")
        assertEquals("10.0 is invalid", manager.messages.first())
    }
}