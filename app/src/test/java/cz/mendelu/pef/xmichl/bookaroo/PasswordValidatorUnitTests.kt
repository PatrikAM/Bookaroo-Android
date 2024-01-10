package cz.mendelu.pef.xmichl.bookaroo

import cz.mendelu.pef.xmichl.bookaroo.extension.isValidPassword
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class PasswordValidatorUnitTests {

    // Priority to do: High
    @Test
    fun password_isNotValid() {
        val passwordInput = "heslo123"
        assertFalse(passwordInput.isValidPassword())
    }

    @Test
    fun password_isNotValid_1() {
        val passwordInput = "heslo"
        assertFalse(passwordInput.isValidPassword())
    }

    @Test
    fun password_isNotValid_2() {
        val passwordInput = "heslo1"
        assertFalse(passwordInput.isValidPassword())
    }

    @Test
    fun password_isNotValid_3() {
        val passwordInput = "123456"
        assertFalse(passwordInput.isValidPassword())
    }

    @Test
    fun password_valid() {
        val passwordInput = "pass.123a"
        assertTrue(passwordInput.isValidPassword(),
        )
    }

}