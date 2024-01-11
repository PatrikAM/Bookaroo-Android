package cz.mendelu.pef.xmichl.bookaroo.extension

import java.util.regex.Pattern.compile

private val emailRegex = compile(
    "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+"
)

fun String.isValidEmail(): Boolean {
    return if (this.contains("@")) {
        emailRegex.matcher(this).matches()
    } else {
        false
    }
}

fun String.isValidPassword(): Boolean {
    if (this.length < 6) {
        return false
    }

    val specialCharacterPattern = Regex("[!@#$%^&*(),.?\":{}|<>]")
    if (!specialCharacterPattern.containsMatchIn(this)) {
        return false
    }

    val digitPattern = Regex("\\d")
    return digitPattern.containsMatchIn(this)
}

fun String.isValidIsbn(): Boolean {

    var cleanedInput = this.cleanNonNumericChars()
    // Remove any hyphens or spaces from the input string
    cleanedInput = this.replace("[\\s-]".toRegex(), "")

    if (cleanedInput.length != 10 && cleanedInput.length != 13) {
        return false
    }

    if (cleanedInput.length == 10) {
        var s = 0
        var t = 0

        for (i in 0 until 10) {
            t += cleanedInput[i].digitToInt()
            s += t
        }
        return s % 11 == 0
    }

    // Check if the input consists of numeric characters only
    if (!cleanedInput.matches("[0-9]+".toRegex())) {
        return false
    }

    // Calculate the checksum
    val checksum = cleanedInput.take(12).map { it.toString().toInt() }
        .mapIndexed { index, digit -> if (index % 2 == 0) digit else digit * 3 }
        .sum() % 10

    // Check if the checksum is correct
    val expectedChecksum = (10 - checksum) % 10
    val actualChecksum = cleanedInput[12].toString().toInt()

    return expectedChecksum == actualChecksum
}

fun String.cleanNonNumericChars(): String {
    return this.replace("[^0-9]".toRegex(), "")
}
