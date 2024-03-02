package dev.programadorthi.state.validator

import dev.programadorthi.state.core.extension.basicValueManager
import dev.programadorthi.state.core.extension.plusAssign
import dev.programadorthi.state.validator.character.InValidator
import dev.programadorthi.state.validator.character.IsDigitValidator
import dev.programadorthi.state.validator.character.IsEqualToValidator
import dev.programadorthi.state.validator.character.IsLetterOrDigitValidator
import dev.programadorthi.state.validator.character.IsLetterValidator
import dev.programadorthi.state.validator.character.IsLowerCaseValidator
import dev.programadorthi.state.validator.character.IsNotDigitValidator
import dev.programadorthi.state.validator.character.IsNotEqualToValidator
import dev.programadorthi.state.validator.character.IsNotLetterOrDigitValidator
import dev.programadorthi.state.validator.character.IsNotLetterValidator
import dev.programadorthi.state.validator.character.IsNotWhitespaceValidator
import dev.programadorthi.state.validator.character.IsUpperCaseValidator
import dev.programadorthi.state.validator.character.IsWhitespaceValidator
import dev.programadorthi.state.validator.character.NotInValidator
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CharacterValidatorsTest {

    @Test
    fun shouldBeValidWithInValidator() {
        // GIVEN
        val manager = basicValueManager('0')
        manager += InValidator(
            values = listOf('1', '2', '3', '4', '5'),
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { '3' }

        // THEN
        assertEquals('3', manager.value)
        assertTrue(manager.isValid, "should be valid using in validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid in validator")
    }

    @Test
    fun shouldBeInvalidWithInValidator() {
        // GIVEN
        val manager = basicValueManager('0')
        manager += InValidator(
            values = listOf('1', '2', '3', '4', '5'),
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { '6' }

        // THEN
        assertEquals('0', manager.value)
        assertFalse(manager.isValid, "should be invalid using in validator")
        assertEquals("6 is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithIsDigitValidator() {
        // GIVEN
        val manager = basicValueManager('0')
        manager += IsDigitValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { '9' }

        // THEN
        assertEquals('9', manager.value)
        assertTrue(manager.isValid, "should be valid using is digit validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid is digit validator")
    }

    @Test
    fun shouldBeInvalidWithIsDigitValidator() {
        // GIVEN
        val manager = basicValueManager('0')
        manager += IsDigitValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { 'D' }

        // THEN
        assertEquals('0', manager.value)
        assertFalse(manager.isValid, "should be invalid using is digit validator")
        assertEquals("D is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithIsEqualToValidator() {
        // GIVEN
        val manager = basicValueManager('0')
        manager += IsEqualToValidator(
            other = 'E',
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { 'E' }

        // THEN
        assertEquals('E', manager.value)
        assertTrue(manager.isValid, "should be valid using is equal to validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid is equal to validator")
    }

    @Test
    fun shouldBeInvalidWithIsEqualToValidator() {
        // GIVEN
        val manager = basicValueManager('0')
        manager += IsEqualToValidator(
            other = 'D',
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { 'E' }

        // THEN
        assertEquals('0', manager.value)
        assertFalse(manager.isValid, "should be invalid using is equal to validator")
        assertEquals("E is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithIsLetterOrDigitValidator() {
        // GIVEN
        val manager = basicValueManager('0')
        manager += IsLetterOrDigitValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { 'E' }

        // THEN
        assertEquals('E', manager.value)
        assertTrue(manager.isValid, "should be valid using is letter or digit validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid is letter or digit validator")
    }

    @Test
    fun shouldBeInvalidWithIsLetterOrDigitValidator() {
        // GIVEN
        val manager = basicValueManager('0')
        manager += IsLetterOrDigitValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { '#' }

        // THEN
        assertEquals('0', manager.value)
        assertFalse(manager.isValid, "should be invalid using is letter or digit validator")
        assertEquals("# is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithIsLetterValidator() {
        // GIVEN
        val manager = basicValueManager('0')
        manager += IsLetterValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { 'E' }

        // THEN
        assertEquals('E', manager.value)
        assertTrue(manager.isValid, "should be valid using is letter validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid is letter validator")
    }

    @Test
    fun shouldBeInvalidWithIsLetterValidator() {
        // GIVEN
        val manager = basicValueManager('0')
        manager += IsLetterValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { '3' }

        // THEN
        assertEquals('0', manager.value)
        assertFalse(manager.isValid, "should be invalid using is letter validator")
        assertEquals("3 is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithIsLowerCaseValidator() {
        // GIVEN
        val manager = basicValueManager('0')
        manager += IsLowerCaseValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { 'e' }

        // THEN
        assertEquals('e', manager.value)
        assertTrue(manager.isValid, "should be valid using is lower case validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid is lower case validator")
    }

    @Test
    fun shouldBeInvalidWithIsLowerCaseValidator() {
        // GIVEN
        val manager = basicValueManager('0')
        manager += IsLowerCaseValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { 'E' }

        // THEN
        assertEquals('0', manager.value)
        assertFalse(manager.isValid, "should be invalid using is lower case validator")
        assertEquals("E is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithIsNotDigitValidator() {
        // GIVEN
        val manager = basicValueManager('0')
        manager += IsNotDigitValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { 'e' }

        // THEN
        assertEquals('e', manager.value)
        assertTrue(manager.isValid, "should be valid using is not digit validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid is not digit validator")
    }

    @Test
    fun shouldBeInvalidWithIsNotDigitValidator() {
        // GIVEN
        val manager = basicValueManager('0')
        manager += IsNotDigitValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { '8' }

        // THEN
        assertEquals('0', manager.value)
        assertFalse(manager.isValid, "should be invalid using is not digit validator")
        assertEquals("8 is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithIsNotEqualToValidator() {
        // GIVEN
        val manager = basicValueManager('0')
        manager += IsNotEqualToValidator(
            other = 'a',
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { 'e' }

        // THEN
        assertEquals('e', manager.value)
        assertTrue(manager.isValid, "should be valid using is not equal to validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid is not equal to validator")
    }

    @Test
    fun shouldBeInvalidWithIsNotEqualToValidator() {
        // GIVEN
        val manager = basicValueManager('0')
        manager += IsNotEqualToValidator(
            other = '8',
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { '8' }

        // THEN
        assertEquals('0', manager.value)
        assertFalse(manager.isValid, "should be invalid using is not equal to validator")
        assertEquals("8 is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithIsNotLetterOrDigitValidator() {
        // GIVEN
        val manager = basicValueManager('0')
        manager += IsNotLetterOrDigitValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { '@' }

        // THEN
        assertEquals('@', manager.value)
        assertTrue(manager.isValid, "should be valid using is not letter or digit validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid is not letter or digit validator")
    }

    @Test
    fun shouldBeInvalidWithIsNotLetterOrDigitValidator() {
        // GIVEN
        val manager = basicValueManager('0')
        manager += IsNotLetterOrDigitValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { '8' }

        // THEN
        assertEquals('0', manager.value)
        assertFalse(manager.isValid, "should be invalid using is not letter or digit validator")
        assertEquals("8 is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithIsNotLetterValidator() {
        // GIVEN
        val manager = basicValueManager('0')
        manager += IsNotLetterValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { '@' }

        // THEN
        assertEquals('@', manager.value)
        assertTrue(manager.isValid, "should be valid using is not letter validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid is not letter validator")
    }

    @Test
    fun shouldBeInvalidWithIsNotLetterValidator() {
        // GIVEN
        val manager = basicValueManager('0')
        manager += IsNotLetterValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { 'a' }

        // THEN
        assertEquals('0', manager.value)
        assertFalse(manager.isValid, "should be invalid using is not letter validator")
        assertEquals("a is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithIsNotWhitespaceValidator() {
        // GIVEN
        val manager = basicValueManager('0')
        manager += IsNotWhitespaceValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { '@' }

        // THEN
        assertEquals('@', manager.value)
        assertTrue(manager.isValid, "should be valid using is not whitespace validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid is not whitespace validator")
    }

    @Test
    fun shouldBeInvalidWithIsNotWhitespaceValidator() {
        // GIVEN
        val manager = basicValueManager('0')
        manager += IsNotWhitespaceValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { ' ' }

        // THEN
        assertEquals('0', manager.value)
        assertFalse(manager.isValid, "should be invalid using is not whitespace validator")
        assertEquals("  is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithIsUpperCaseValidator() {
        // GIVEN
        val manager = basicValueManager('0')
        manager += IsUpperCaseValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { 'P' }

        // THEN
        assertEquals('P', manager.value)
        assertTrue(manager.isValid, "should be valid using is upper case validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid is upper case validator")
    }

    @Test
    fun shouldBeInvalidWithIsUpperCaseValidator() {
        // GIVEN
        val manager = basicValueManager('0')
        manager += IsUpperCaseValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { 'p' }

        // THEN
        assertEquals('0', manager.value)
        assertFalse(manager.isValid, "should be invalid using is upper case validator")
        assertEquals("p is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithIsWhitespaceValidator() {
        // GIVEN
        val manager = basicValueManager('0')
        manager += IsWhitespaceValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { ' ' }

        // THEN
        assertEquals(' ', manager.value)
        assertTrue(manager.isValid, "should be valid using is whitespace validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid is whitespace validator")
    }

    @Test
    fun shouldBeInvalidWithIsWhitespaceValidator() {
        // GIVEN
        val manager = basicValueManager('0')
        manager += IsWhitespaceValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { 'p' }

        // THEN
        assertEquals('0', manager.value)
        assertFalse(manager.isValid, "should be invalid using is whitespace validator")
        assertEquals("p is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithNotInValidator() {
        // GIVEN
        val manager = basicValueManager('0')
        manager += NotInValidator(
            values = listOf('1', '2', '3', '4', '5'),
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { '9' }

        // THEN
        assertEquals('9', manager.value)
        assertTrue(manager.isValid, "should be valid using not in validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid not in validator")
    }

    @Test
    fun shouldBeInvalidWithNotInValidator() {
        // GIVEN
        val manager = basicValueManager('0')
        manager += NotInValidator(
            values = listOf('1', '2', '3', '4', '5'),
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { '4' }

        // THEN
        assertEquals('0', manager.value)
        assertFalse(manager.isValid, "should be invalid using not in validator")
        assertEquals("4 is invalid", manager.messages.first())
    }
}