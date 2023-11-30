package cz.mendelu.pef.xmichl.bookaroo.ui.screens.login

interface LoginScreenActions {
    fun login(username: String, password: String)
    fun logout()
    fun onUsernameChanged(username: String?)
}
