package cz.mendelu.pef.xmichl.bookaroo.ui.screens.login

interface RegisterScreenActions {
    fun register(
        username: String,
        password: String,
        name: String
    )

    fun onUsernameChanged(username: String?)

    fun onErrorDismiss()
}