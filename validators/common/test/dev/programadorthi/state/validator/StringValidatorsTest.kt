package dev.programadorthi.state.validator

import dev.programadorthi.state.core.extension.basicValueManager
import dev.programadorthi.state.core.extension.plusAssign
import dev.programadorthi.state.validator.string.AllDigitValidator
import dev.programadorthi.state.validator.string.AllLetterOrDigitValidator
import dev.programadorthi.state.validator.string.AllLetterValidator
import dev.programadorthi.state.validator.string.AllLowerCaseValidator
import dev.programadorthi.state.validator.string.AllUpperCaseValidator
import dev.programadorthi.state.validator.string.AllWhitespaceValidator
import dev.programadorthi.state.validator.string.AnyDigitValidator
import dev.programadorthi.state.validator.string.AnyLetterOrDigitValidator
import dev.programadorthi.state.validator.string.AnyLetterValidator
import dev.programadorthi.state.validator.string.AnyLowerCaseValidator
import dev.programadorthi.state.validator.string.AnyUpperCaseValidator
import dev.programadorthi.state.validator.string.AnyWhitespaceValidator
import dev.programadorthi.state.validator.string.ContainsAllValidator
import dev.programadorthi.state.validator.string.ContainsAnyValidator
import dev.programadorthi.state.validator.string.ContainsValidator
import dev.programadorthi.state.validator.string.EndsWithValidator
import dev.programadorthi.state.validator.string.HasSizeValidator
import dev.programadorthi.state.validator.string.InValidator
import dev.programadorthi.state.validator.string.IsEqualToValidator
import dev.programadorthi.state.validator.string.IsNotBlankValidator
import dev.programadorthi.state.validator.string.IsNotEmptyValidator
import dev.programadorthi.state.validator.string.IsNotEqualToValidator
import dev.programadorthi.state.validator.string.IsNullOrBlankValidator
import dev.programadorthi.state.validator.string.IsNullOrEmptyValidator
import dev.programadorthi.state.validator.string.NoneDigitValidator
import dev.programadorthi.state.validator.string.NoneLetterOrDigitValidator
import dev.programadorthi.state.validator.string.NoneLetterValidator
import dev.programadorthi.state.validator.string.NoneLowerCaseValidator
import dev.programadorthi.state.validator.string.NoneUpperCaseValidator
import dev.programadorthi.state.validator.string.NoneWhitespaceValidator
import dev.programadorthi.state.validator.string.NotContainsAllValidator
import dev.programadorthi.state.validator.string.NotContainsAnyValidator
import dev.programadorthi.state.validator.string.NotContainsValidator
import dev.programadorthi.state.validator.string.NotEndsWithValidator
import dev.programadorthi.state.validator.string.NotInValidator
import dev.programadorthi.state.validator.string.NotStartsWithValidator
import dev.programadorthi.state.validator.string.RegexContainsValidator
import dev.programadorthi.state.validator.string.RegexMatchValidator
import dev.programadorthi.state.validator.string.RegexNotContainsValidator
import dev.programadorthi.state.validator.string.RegexNotMatchValidator
import dev.programadorthi.state.validator.string.StartsWithValidator
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class StringValidatorsTest {

    @Test
    fun shouldBeValidWithAllDigitValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += AllDigitValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "123456" }

        // THEN
        assertEquals("123456", manager.value)
        assertTrue(manager.isValid, "should be valid using all digit validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid all digit validator")
    }

    @Test
    fun shouldBeInvalidWithAllDigitValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += AllDigitValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "123abc456" }

        // THEN
        assertEquals("", manager.value)
        assertFalse(manager.isValid, "should be invalid using all digit validator")
        assertEquals("123abc456 is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithAllLetterOrDigitValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += AllLetterOrDigitValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "123abc456" }

        // THEN
        assertEquals("123abc456", manager.value)
        assertTrue(manager.isValid, "should be valid using all letter or digit validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid all letter or digit validator")
    }

    @Test
    fun shouldBeInvalidWithAllLetterOrDigitValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += AllLetterOrDigitValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "123abc456@#$%" }

        // THEN
        assertEquals("", manager.value)
        assertFalse(manager.isValid, "should be invalid using all letter or digit validator")
        assertEquals("123abc456@#$% is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithAllLetterValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += AllLetterValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "abc" }

        // THEN
        assertEquals("abc", manager.value)
        assertTrue(manager.isValid, "should be valid using all letter validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid all letter validator")
    }

    @Test
    fun shouldBeInvalidWithAllLetterValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += AllLetterValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "123abc" }

        // THEN
        assertEquals("", manager.value)
        assertFalse(manager.isValid, "should be invalid using all letter validator")
        assertEquals("123abc is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithAllLowerCaseValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += AllLowerCaseValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "abc" }

        // THEN
        assertEquals("abc", manager.value)
        assertTrue(manager.isValid, "should be valid using all lower case validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid all lower case validator")
    }

    @Test
    fun shouldBeInvalidWithAllLowerCaseValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += AllLowerCaseValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "abCdeF" }

        // THEN
        assertEquals("", manager.value)
        assertFalse(manager.isValid, "should be invalid using all lower case validator")
        assertEquals("abCdeF is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithAllUpperCaseValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += AllUpperCaseValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "ABC" }

        // THEN
        assertEquals("ABC", manager.value)
        assertTrue(manager.isValid, "should be valid using all upper case validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid all upper case validator")
    }

    @Test
    fun shouldBeInvalidWithAllUpperCaseValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += AllUpperCaseValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "abCdeF" }

        // THEN
        assertEquals("", manager.value)
        assertFalse(manager.isValid, "should be invalid using all upper case validator")
        assertEquals("abCdeF is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithAllWhitespaceValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += AllWhitespaceValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "         " }

        // THEN
        assertEquals("         ", manager.value)
        assertTrue(manager.isValid, "should be valid using all whitespace validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid all whitespace validator")
    }

    @Test
    fun shouldBeInvalidWithAllWhitespaceValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += AllWhitespaceValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "     a  " }

        // THEN
        assertEquals("", manager.value)
        assertFalse(manager.isValid, "should be invalid using all whitespace validator")
        assertEquals("     a   is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithAnyDigitValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += AnyDigitValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "ab12cdthff445" }

        // THEN
        assertEquals("ab12cdthff445", manager.value)
        assertTrue(manager.isValid, "should be valid using any digit validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid any digit validator")
    }

    @Test
    fun shouldBeInvalidWithAnyDigitValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += AnyDigitValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "abc@#$" }

        // THEN
        assertEquals("", manager.value)
        assertFalse(manager.isValid, "should be invalid using any digit validator")
        assertEquals("abc@#$ is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithAnyLetterOrDigitValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += AnyLetterOrDigitValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "123abc456" }

        // THEN
        assertEquals("123abc456", manager.value)
        assertTrue(manager.isValid, "should be valid using any letter or digit validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid any letter or digit validator")
    }

    @Test
    fun shouldBeInvalidWithAnyLetterOrDigitValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += AnyLetterOrDigitValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "@#$%" }

        // THEN
        assertEquals("", manager.value)
        assertFalse(manager.isValid, "should be invalid using any letter or digit validator")
        assertEquals("@#$% is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithAnyLetterValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += AnyLetterValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "123bf456hytu8" }

        // THEN
        assertEquals("123bf456hytu8", manager.value)
        assertTrue(manager.isValid, "should be valid using any letter validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid any letter validator")
    }

    @Test
    fun shouldBeInvalidWithAnyLetterValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += AnyLetterValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "123@#$%" }

        // THEN
        assertEquals("", manager.value)
        assertFalse(manager.isValid, "should be invalid using any letter validator")
        assertEquals("123@#$% is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithAnyLowerCaseValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += AnyLowerCaseValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "AMNCerdt4567" }

        // THEN
        assertEquals("AMNCerdt4567", manager.value)
        assertTrue(manager.isValid, "should be valid using any lower case validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid any lower case validator")
    }

    @Test
    fun shouldBeInvalidWithAnyLowerCaseValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += AnyLowerCaseValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "AMNC4567" }

        // THEN
        assertEquals("", manager.value)
        assertFalse(manager.isValid, "should be invalid using any lower case validator")
        assertEquals("AMNC4567 is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithAnyUpperCaseValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += AnyUpperCaseValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "testAB2345" }

        // THEN
        assertEquals("testAB2345", manager.value)
        assertTrue(manager.isValid, "should be valid using any upper case validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid any upper case validator")
    }

    @Test
    fun shouldBeInvalidWithAnyUpperCaseValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += AnyUpperCaseValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "abc123de456" }

        // THEN
        assertEquals("", manager.value)
        assertFalse(manager.isValid, "should be invalid using any upper case validator")
        assertEquals("abc123de456 is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithAnyWhitespaceValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += AnyWhitespaceValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "abc 123 " }

        // THEN
        assertEquals("abc 123 ", manager.value)
        assertTrue(manager.isValid, "should be valid using any whitespace validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid any whitespace validator")
    }

    @Test
    fun shouldBeInvalidWithAnyWhitespaceValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += AnyWhitespaceValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "VALUE_WITH_NO_SPACE" }

        // THEN
        assertEquals("", manager.value)
        assertFalse(manager.isValid, "should be invalid using any whitespace validator")
        assertEquals("VALUE_WITH_NO_SPACE is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithContainsAllValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += ContainsAllValidator(
            targets = listOf("ab", "cd", "ef"),
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "abcdef" }

        // THEN
        assertEquals("abcdef", manager.value)
        assertTrue(manager.isValid, "should be valid using contains all validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid contains all validator")
    }

    @Test
    fun shouldBeInvalidWithContainsAllValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += ContainsAllValidator(
            targets = listOf("ab", "cd", "ef"),
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "cd" }

        // THEN
        assertEquals("", manager.value)
        assertFalse(manager.isValid, "should be invalid using contains all validator")
        assertEquals("cd is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithContainsAnyValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += ContainsAnyValidator(
            targets = listOf("ab", "cd", "ef"),
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "cd" }

        // THEN
        assertEquals("cd", manager.value)
        assertTrue(manager.isValid, "should be valid using contains any validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid contains any validator")
    }

    @Test
    fun shouldBeInvalidWithContainsAnyValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += ContainsAnyValidator(
            targets = listOf("ab", "cd", "ef"),
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "gh" }

        // THEN
        assertEquals("", manager.value)
        assertFalse(manager.isValid, "should be invalid using contains any validator")
        assertEquals("gh is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithContainsValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += ContainsValidator(
            target = "xy",
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "opqrstuvwxyz" }

        // THEN
        assertEquals("opqrstuvwxyz", manager.value)
        assertTrue(manager.isValid, "should be valid using contains validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid contains validator")
    }

    @Test
    fun shouldBeInvalidWithContainsValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += ContainsValidator(
            target = "xy",
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "opqrstuvwz" }

        // THEN
        assertEquals("", manager.value)
        assertFalse(manager.isValid, "should be invalid using contains validator")
        assertEquals("opqrstuvwz is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithEndsWithValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += EndsWithValidator(
            suffix = "xyz",
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "opqrstuvwxyz" }

        // THEN
        assertEquals("opqrstuvwxyz", manager.value)
        assertTrue(manager.isValid, "should be valid using ends with validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid ends with validator")
    }

    @Test
    fun shouldBeInvalidWithEndsWithValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += EndsWithValidator(
            suffix = "xyz",
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "opqrstuvw" }

        // THEN
        assertEquals("", manager.value)
        assertFalse(manager.isValid, "should be invalid using ends with validator")
        assertEquals("opqrstuvw is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithHasSizeValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += HasSizeValidator(
            minSize = 3,
            maxSize = 10,
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "abcdef" }

        // THEN
        assertEquals("abcdef", manager.value)
        assertTrue(manager.isValid, "should be valid using has size validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid has size validator")
    }

    @Test
    fun shouldBeInvalidWithHasSizeValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += HasSizeValidator(
            minSize = 3,
            maxSize = 10,
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "a" }

        // THEN
        assertEquals("", manager.value)
        assertFalse(manager.isValid, "should be invalid using has size validator")
        assertEquals("a is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithInValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += InValidator(
            values = listOf("abc", "123", "xyz"),
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "123" }

        // THEN
        assertEquals("123", manager.value)
        assertTrue(manager.isValid, "should be valid using in validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid in validator")
    }

    @Test
    fun shouldBeInvalidWithInValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += InValidator(
            values = listOf("abc", "123", "xyz"),
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "asdf" }

        // THEN
        assertEquals("", manager.value)
        assertFalse(manager.isValid, "should be invalid using in validator")
        assertEquals("asdf is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithIsEqualToValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += IsEqualToValidator(
            other = "abc123",
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "abc123" }

        // THEN
        assertEquals("abc123", manager.value)
        assertTrue(manager.isValid, "should be valid using is equal to validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid is equal to validator")
    }

    @Test
    fun shouldBeInvalidWithIsEqualToValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += IsEqualToValidator(
            other = "abc123",
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "xyz" }

        // THEN
        assertEquals("", manager.value)
        assertFalse(manager.isValid, "should be invalid using is equal to validator")
        assertEquals("xyz is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithIsNotBlankValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += IsNotBlankValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "abc123" }

        // THEN
        assertEquals("abc123", manager.value)
        assertTrue(manager.isValid, "should be valid using is not blank validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid is not blank validator")
    }

    @Test
    fun shouldBeInvalidWithIsNotBlankValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += IsNotBlankValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { " " }

        // THEN
        assertEquals("", manager.value)
        assertFalse(manager.isValid, "should be invalid using is not blank validator")
        assertEquals("  is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithIsNotEmptyValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += IsNotEmptyValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "abc123" }

        // THEN
        assertEquals("abc123", manager.value)
        assertTrue(manager.isValid, "should be valid using is not empty validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid is not empty validator")
    }

    @Test
    fun shouldBeInvalidWithIsNotEmptyValidator() {
        // GIVEN
        val manager = basicValueManager("a")
        manager += IsNotEmptyValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "" }

        // THEN
        assertEquals("a", manager.value)
        assertFalse(manager.isValid, "should be invalid using is not empty validator")
        assertEquals(" is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithIsNotEqualToValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += IsNotEqualToValidator(
            other = "xyz",
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "abc" }

        // THEN
        assertEquals("abc", manager.value)
        assertTrue(manager.isValid, "should be valid using is not equal to validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid is not equal to validator")
    }

    @Test
    fun shouldBeInvalidWithIsNotEqualToValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += IsNotEqualToValidator(
            other = "123",
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "123" }

        // THEN
        assertEquals("", manager.value)
        assertFalse(manager.isValid, "should be invalid using is not equal to validator")
        assertEquals("123 is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithIsNullOrBlankValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += IsNullOrBlankValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { " " }

        // THEN
        assertEquals(" ", manager.value)
        assertTrue(manager.isValid, "should be valid using is null or blank validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid is null or blank validator")
    }

    @Test
    fun shouldBeInvalidWithIsNullOrBlankValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += IsNullOrBlankValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "123" }

        // THEN
        assertEquals("", manager.value)
        assertFalse(manager.isValid, "should be invalid using is null or blank validator")
        assertEquals("123 is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithIsNullOrEmptyValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += IsNullOrEmptyValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "" }

        // THEN
        assertEquals("", manager.value)
        assertTrue(manager.isValid, "should be valid using is null or empty validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid is null or empty validator")
    }

    @Test
    fun shouldBeInvalidWithIsNullOrEmptyValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += IsNullOrEmptyValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "123" }

        // THEN
        assertEquals("", manager.value)
        assertFalse(manager.isValid, "should be invalid using is null or empty validator")
        assertEquals("123 is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithNoneDigitValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += NoneDigitValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "abc" }

        // THEN
        assertEquals("abc", manager.value)
        assertTrue(manager.isValid, "should be valid using none digit validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid none digit validator")
    }

    @Test
    fun shouldBeInvalidWithNoneDigitValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += NoneDigitValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "123" }

        // THEN
        assertEquals("", manager.value)
        assertFalse(manager.isValid, "should be invalid using none digit validator")
        assertEquals("123 is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithNoneLetterOrDigitValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += NoneLetterOrDigitValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "!@#$%" }

        // THEN
        assertEquals("!@#$%", manager.value)
        assertTrue(manager.isValid, "should be valid using none letter or digit validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid none letter or digit validator")
    }

    @Test
    fun shouldBeInvalidWithNoneLetterOrDigitValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += NoneLetterOrDigitValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "123" }

        // THEN
        assertEquals("", manager.value)
        assertFalse(manager.isValid, "should be invalid using none letter or digit validator")
        assertEquals("123 is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithNoneLetterValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += NoneLetterValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "123" }

        // THEN
        assertEquals("123", manager.value)
        assertTrue(manager.isValid, "should be valid using none letter validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid none letter validator")
    }

    @Test
    fun shouldBeInvalidWithNoneLetterValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += NoneLetterValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "abc" }

        // THEN
        assertEquals("", manager.value)
        assertFalse(manager.isValid, "should be invalid using none letter validator")
        assertEquals("abc is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithNoneLowerCaseValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += NoneLowerCaseValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "ABC" }

        // THEN
        assertEquals("ABC", manager.value)
        assertTrue(manager.isValid, "should be valid using none lower case validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid none lower case validator")
    }

    @Test
    fun shouldBeInvalidWithNoneLowerCaseValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += NoneLowerCaseValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "abc" }

        // THEN
        assertEquals("", manager.value)
        assertFalse(manager.isValid, "should be invalid using none lower case validator")
        assertEquals("abc is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithNoneUpperCaseValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += NoneUpperCaseValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "abc" }

        // THEN
        assertEquals("abc", manager.value)
        assertTrue(manager.isValid, "should be valid using none upper case validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid none upper case validator")
    }

    @Test
    fun shouldBeInvalidWithNoneUpperCaseValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += NoneUpperCaseValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "ABC" }

        // THEN
        assertEquals("", manager.value)
        assertFalse(manager.isValid, "should be invalid using none upper case validator")
        assertEquals("ABC is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithNoneWhitespaceValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += NoneWhitespaceValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "abc" }

        // THEN
        assertEquals("abc", manager.value)
        assertTrue(manager.isValid, "should be valid using none whitespace validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid none whitespace validator")
    }

    @Test
    fun shouldBeInvalidWithNoneWhitespaceValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += NoneWhitespaceValidator(
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { " " }

        // THEN
        assertEquals("", manager.value)
        assertFalse(manager.isValid, "should be invalid using none whitespace validator")
        assertEquals("  is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithNotContainsAllValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += NotContainsAllValidator(
            targets = listOf("12", "34", "56"),
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "12ab56" }

        // THEN
        assertEquals("12ab56", manager.value)
        assertTrue(manager.isValid, "should be valid using not contains all validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid not contains all validator")
    }

    @Test
    fun shouldBeInvalidWithNotContainsAllValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += NotContainsAllValidator(
            targets = listOf("12", "34", "56"),
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "123456" }

        // THEN
        assertEquals("", manager.value)
        assertFalse(manager.isValid, "should be invalid using not contains all validator")
        assertEquals("123456 is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithNotContainsAnyValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += NotContainsAnyValidator(
            targets = listOf("12", "34", "56"),
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "ab" }

        // THEN
        assertEquals("ab", manager.value)
        assertTrue(manager.isValid, "should be valid using not contains any validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid not contains any validator")
    }

    @Test
    fun shouldBeInvalidWithNotContainsAnyValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += NotContainsAnyValidator(
            targets = listOf("12", "34", "56"),
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "ab34ef" }

        // THEN
        assertEquals("", manager.value)
        assertFalse(manager.isValid, "should be invalid using not contains any validator")
        assertEquals("ab34ef is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithNotContainsValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += NotContainsValidator(
            target = "34",
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "ab12ef" }

        // THEN
        assertEquals("ab12ef", manager.value)
        assertTrue(manager.isValid, "should be valid using not contains validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid not contains validator")
    }

    @Test
    fun shouldBeInvalidWithNotContainsValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += NotContainsValidator(
            target = "34",
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "ab34ef" }

        // THEN
        assertEquals("", manager.value)
        assertFalse(manager.isValid, "should be invalid using not contains validator")
        assertEquals("ab34ef is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithNotEndsWithValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += NotEndsWithValidator(
            suffix = "ly",
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "week" }

        // THEN
        assertEquals("week", manager.value)
        assertTrue(manager.isValid, "should be valid using not ends with validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid not ends with validator")
    }

    @Test
    fun shouldBeInvalidWithNotEndsWithValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += NotEndsWithValidator(
            suffix = "ly",
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "weekly" }

        // THEN
        assertEquals("", manager.value)
        assertFalse(manager.isValid, "should be invalid using not ends with validator")
        assertEquals("weekly is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithNotInValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += NotInValidator(
            values = listOf("12", "34", "56", "78"),
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "90" }

        // THEN
        assertEquals("90", manager.value)
        assertTrue(manager.isValid, "should be valid using not in validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid not in validator")
    }

    @Test
    fun shouldBeInvalidWithNotInValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += NotInValidator(
            values = listOf("12", "34", "56", "78"),
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "56" }

        // THEN
        assertEquals("", manager.value)
        assertFalse(manager.isValid, "should be invalid using not in validator")
        assertEquals("56 is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithNotStartsWithValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += NotStartsWithValidator(
            prefix = "an",
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "droid" }

        // THEN
        assertEquals("droid", manager.value)
        assertTrue(manager.isValid, "should be valid using not starts with validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid not starts with validator")
    }

    @Test
    fun shouldBeInvalidWithNotStartsWithValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += NotStartsWithValidator(
            prefix = "an",
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "android" }

        // THEN
        assertEquals("", manager.value)
        assertFalse(manager.isValid, "should be invalid using not starts with validator")
        assertEquals("android is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithRegexContainsValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += RegexContainsValidator(
            regex = """\d""".toRegex(),
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "24droid" }

        // THEN
        assertEquals("24droid", manager.value)
        assertTrue(manager.isValid, "should be valid using regex contains validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid regex contains validator")
    }

    @Test
    fun shouldBeInvalidWithRegexContainsValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += RegexContainsValidator(
            regex = """\d""".toRegex(),
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "kotlin" }

        // THEN
        assertEquals("", manager.value)
        assertFalse(manager.isValid, "should be invalid using regex contains validator")
        assertEquals("kotlin is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithRegexMatchValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += RegexMatchValidator(
            regex = """\d{4}""".toRegex(),
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "2024" }

        // THEN
        assertEquals("2024", manager.value)
        assertTrue(manager.isValid, "should be valid using regex matches validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid regex matches validator")
    }

    @Test
    fun shouldBeInvalidWithRegexMatchValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += RegexMatchValidator(
            regex = """\d{4}""".toRegex(),
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "24" }

        // THEN
        assertEquals("", manager.value)
        assertFalse(manager.isValid, "should be invalid using regex matches validator")
        assertEquals("24 is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithRegexNotContainsValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += RegexNotContainsValidator(
            regex = """\d""".toRegex(),
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "android" }

        // THEN
        assertEquals("android", manager.value)
        assertTrue(manager.isValid, "should be valid using regex not contains validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid regex not contains validator")
    }

    @Test
    fun shouldBeInvalidWithRegexNotContainsValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += RegexNotContainsValidator(
            regex = """\d""".toRegex(),
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "24droid" }

        // THEN
        assertEquals("", manager.value)
        assertFalse(manager.isValid, "should be invalid using regex not contains validator")
        assertEquals("24droid is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithRegexNotMatchValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += RegexNotMatchValidator(
            regex = """\d{4}""".toRegex(),
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "24" }

        // THEN
        assertEquals("24", manager.value)
        assertTrue(manager.isValid, "should be valid using regex not matches validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid regex not matches validator")
    }

    @Test
    fun shouldBeInvalidWithRegexNotMatchValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += RegexNotMatchValidator(
            regex = """\d{4}""".toRegex(),
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "2024" }

        // THEN
        assertEquals("", manager.value)
        assertFalse(manager.isValid, "should be invalid using regex not matches validator")
        assertEquals("2024 is invalid", manager.messages.first())
    }

    @Test
    fun shouldBeValidWithStartsWithValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += StartsWithValidator(
            prefix = "an",
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "android" }

        // THEN
        assertEquals("android", manager.value)
        assertTrue(manager.isValid, "should be valid using starts with validator")
        assertTrue(manager.messages.isEmpty(), "should messages be empty having valid starts with validator")
    }

    @Test
    fun shouldBeInvalidWithStartsWithValidator() {
        // GIVEN
        val manager = basicValueManager("")
        manager += StartsWithValidator(
            prefix = "an",
            message = { "$it is invalid" }
        )

        // WHEN
        manager.update { "droid" }

        // THEN
        assertEquals("", manager.value)
        assertFalse(manager.isValid, "should be invalid using starts with validator")
        assertEquals("droid is invalid", manager.messages.first())
    }
}