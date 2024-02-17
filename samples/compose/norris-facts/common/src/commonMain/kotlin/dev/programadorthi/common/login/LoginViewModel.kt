package dev.programadorthi.common.login

import dev.programadorthi.state.core.extension.basicValueManager
import dev.programadorthi.state.core.extension.plusAssign
import dev.programadorthi.state.validator.string.HasSizeValidator
import dev.programadorthi.state.validator.string.IsEqualToValidator
import dev.programadorthi.state.validator.string.IsNotBlankValidator

class LoginViewModel {
    val username = basicValueManager(initialValue = "")
    val password = basicValueManager(initialValue = "")

    init {
        username += IsNotBlankValidator(message = { "Username is required" })
        username += IsEqualToValidator(
            other = "admin",
            ignoreCase = true,
            message = { "Username '$it' must be equals to admin" },
        )

        password += IsNotBlankValidator(message = { "Password is required" })
        password += HasSizeValidator(
            minSize = 6,
            maxSize = 20,
            message = { "Password length must not be less than 6 or greater than 20" }
        )
    }

    fun login() {
        val uValid = username.validate()
        val pValid = password.validate()
        if (uValid && pValid) {
            println(">>>> Signed in!!")
        }
    }
}