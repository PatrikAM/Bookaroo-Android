package cz.mendelu.pef.xmichl.bookaroo.extension

import android.util.Patterns
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
