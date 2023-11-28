package cz.mendelu.pef.xmichl.bookaroo.ui.screens.login

data class LoginErrors(
    var communicationError: Int? = null,
    var usernameError: Int? = null,
    var passwordError: Int? = null,
)
