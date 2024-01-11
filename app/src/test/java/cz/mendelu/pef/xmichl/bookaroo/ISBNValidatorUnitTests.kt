package cz.mendelu.pef.xmichl.bookaroo

import cz.mendelu.pef.xmichl.bookaroo.extension.isValidIsbn
import org.junit.Assert
import org.junit.Test

class ISBNValidatorUnitTests {

    @Test
    fun isbn13_isValid() {
        val isbn = "978-0-306-40615-7"
        Assert.assertTrue(isbn.isValidIsbn())
    }

    @Test
    fun isbn13_isNotValid() {
        val isbn = "978-0-306-40615-1"
        Assert.assertFalse(isbn.isValidIsbn())
    }

    @Test
    fun isbn10_isValid() {
        val isbn = "0-306-40615-2"
        Assert.assertTrue(isbn.isValidIsbn())
    }

    @Test
    fun isbn10_isNotValid() {
        val isbn = "0-306-40615-1"
        Assert.assertFalse(isbn.isValidIsbn())
    }

    @Test
    fun notIsbnLength_isNotValid() {
        val isbn = "0-306-406"
        Assert.assertFalse(isbn.isValidIsbn())
    }

    @Test
    fun notIsbnChars_isNotValid() {
        val isbn = "0-306-406dawdawdawdawd"
        Assert.assertFalse(isbn.isValidIsbn())
    }

    @Test
    fun notIsbn_isNotValid() {
        val isbn = "7590285454732"
        Assert.assertFalse(isbn.isValidIsbn())
    }

}