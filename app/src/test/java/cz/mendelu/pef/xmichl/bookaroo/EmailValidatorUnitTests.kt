package cz.mendelu.pef.xmichl.bookaroo

import cz.mendelu.pef.xmichl.bookaroo.extension.isValidEmail
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class EmailValidatorUnitTests {

    @Test
    fun email_isValid() {
        val emailInput = "email@test.com"
        assertTrue(emailInput.isValidEmail())
    }

    @Test
    fun email_isNotValid_1() {
        val emailInput = "emailtest.com"
        assertFalse(emailInput.isValidEmail())
    }

    @Test
    fun email_isNotValid_2() {
        val emailInput = "email@testcom"
        assertFalse(emailInput.isValidEmail())
    }

    @Test
    fun email_isNotValid_3() {
        val emailInput = "emailtestcom"
        assertFalse(emailInput.isValidEmail())
    }

    @Test
    fun email_isNotValid_4() {
        val emailInput = "emailtestcom"
        assertFalse(emailInput.isValidEmail())
    }

    // Priority to do: Low
    @Test
    fun email_isNotValid_5() {
        val emailInput = "email@@test.com"
        assertFalse(emailInput.isValidEmail())
    }

    // Priority to do: Low
    @Test
    fun email_isNotValid_5_same_but_different() {
        val emailInput = "email@@test.com"
        assertFalse(emailInput.isValidEmail())
    }

    // Priority to do: Low
    @Test
    fun email_isNotValid_evil() {
        val emailInput = "emailtestcom"
        assertFalse(emailInput.isValidEmail())
    }
}
