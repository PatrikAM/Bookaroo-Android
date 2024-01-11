package cz.mendelu.pef.xmichl.bookaroo.ui.screens.login

import android.util.Patterns
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cz.mendelu.pef.xmichl.bookaroo.R
import cz.mendelu.pef.xmichl.bookaroo.architecture.BaseViewModel
import cz.mendelu.pef.xmichl.bookaroo.architecture.CommunicationResult
import cz.mendelu.pef.xmichl.bookaroo.communication.reader.IReaderRemoteRepository
import cz.mendelu.pef.xmichl.bookaroo.datastore.DataStoreRepositoryImpl
import cz.mendelu.pef.xmichl.bookaroo.datastore.IDataStoreRepository
import cz.mendelu.pef.xmichl.bookaroo.extension.isValidEmail
import cz.mendelu.pef.xmichl.bookaroo.extension.isValidPassword
import cz.mendelu.pef.xmichl.bookaroo.model.Reader
import cz.mendelu.pef.xmichl.bookaroo.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInUpViewModel
@Inject constructor(
    private val repository: IReaderRemoteRepository,
    private val dataStoreRepo: DataStoreRepositoryImpl
) : BaseViewModel(), LoginScreenActions, RegisterScreenActions {


    val loginUIState: MutableState<UiState<Reader, LoginErrors>> =
        mutableStateOf(UiState())

    init {
        loginUIState.value = UiState(
            loading = false,
            data = null,
            errors = loginUIState.value.errors
        )
        launch {
//            val a = dataStoreRepo.getLoginSuccessful()
//            if (a) {
//                loginUIState.value = UiState(
//                    loading = true,
//                    data = PetStoreApiResponse(
//                        code = null,
//                        type = null,
//                        message = null
//                    ),
//                    errors = null
//                )
//            } else {
//                loginUIState.value = UiState(
//                    loading = false,
//                    data = null,
//                    errors = null
//                )
//            }
        }
    }

    var data = LoginData()

    override fun login(username: String, password: String) {
        if (!isUserNameValid(username) || !isPasswordValid(password)) {
            if (loginUIState.value.errors == null) {
                loginUIState.value.errors = LoginErrors()
            }

            if (!isPasswordValid(password))
//                data.passwordError = R.string.password_length_7
                loginUIState.value.errors!!.passwordError = R.string.password_length_6
            else
                loginUIState.value.errors!!.passwordError = null

            if (!isUserNameValid(username))
                loginUIState.value.errors!!.usernameError = R.string.email_is_not_valid
            else
                loginUIState.value.errors!!.usernameError = null

            loginUIState.value.errors!!.communicationError = R.string.failed_to_log_in

            loginUIState.value = UiState(
                loading = false,
                data = null,
                errors = loginUIState.value.errors
            )
        } else {
            launch {
                when (val result = repository.login(login = username, password = password)) {
                    is CommunicationResult.CommunicationError -> {
                        loginUIState.value = UiState(
                            loading = false,
                            data = null,
                            errors = LoginErrors(R.string.no_internet_connection, showError = true)
                        )
                    }

                    is CommunicationResult.Error -> {
                        loginUIState.value = UiState(
                            loading = false,
                            data = null,
                            errors = LoginErrors(R.string.failed_to_log_in, showError = true)
                        )
                    }

                    is CommunicationResult.Exception -> {
                        loginUIState.value = UiState(
                            loading = false,
                            data = null,
                            errors = LoginErrors(R.string.unknown_error, showError = true)
                        )
                    }

                    is CommunicationResult.Success -> {
                        loginUIState.value = UiState(
                            loading = true,
                            data = result.data,
                            errors = null
                        )
//                        dataStoreRepo.setLoginSuccessful()
                        dataStoreRepo.setUserToken(result.data.token)
                    }
                }
            }
        }
    }

    override fun logout() {
        launch {
            dataStoreRepo.deleteUserToken()
            loginUIState.value = UiState(
                loading = true,
                data = null,
                errors = null,
                actionDone = true
            )
        }
//        launch {
//            when (val result = repository.logout()) {
//                is CommunicationResult.CommunicationError -> {
//                    loginUIState.value = UiState(
//                        loading = false,
//                        data = null,
//                        errors = LoginErrors(R.string.no_internet_connection)
//                    )
//                }
//
//                is CommunicationResult.Error -> {
//                    loginUIState.value = UiState(
//                        loading = false,
//                        data = null,
//                        errors = LoginErrors(R.string.failed_to_log_in)
//                    )
//                }
//
//                is CommunicationResult.Exception -> {
//                    loginUIState.value = UiState(
//                        loading = false,
//                        data = null,
//                        errors = LoginErrors(R.string.unknown_error)
//                    )
//                }
//
//                is CommunicationResult.Success -> {
////                    loginUIState.value = UiState(
////                        loading = false,
////                        data = result.data,
////                        errors = null
////                    )
////                    dataStoreRepo.setLogoutSuccessful()
//                }
//            }
//        }
    }

    private fun isUserNameValid(username: String): Boolean {
        return  username.isValidEmail()
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.isValidPassword()
    }

    private fun checkFormQualityAndSetErrors(): Boolean {
        if (!isUserNameValid(data.username) || !isPasswordValid(data.password)) {
            if (loginUIState.value.errors == null) {
                loginUIState.value.errors = LoginErrors()
            }

            if (!isPasswordValid(data.password))
                loginUIState.value.errors!!.passwordError = R.string.password_length_6
            else
                loginUIState.value.errors!!.passwordError = null

            if (!isUserNameValid(data.username))
                loginUIState.value.errors!!.usernameError = R.string.email_is_not_valid
            else
                loginUIState.value.errors!!.usernameError = null

            loginUIState.value.errors!!.communicationError = R.string.failed_to_log_in

            loginUIState.value = UiState(
                loading = false,
                data = null,
                errors = loginUIState.value.errors
            )
            return true
        }
        return false
    }

    override fun register(username: String, password: String, name: String) {
        if (!isUserNameValid(username) || !isPasswordValid(password)) {
            if (loginUIState.value.errors == null) {
                loginUIState.value.errors = LoginErrors()
            }

            if (!isPasswordValid(password))
//                data.passwordError = R.string.password_length_7
                loginUIState.value.errors!!.passwordError = R.string.password_length_6
            else
                loginUIState.value.errors!!.passwordError = null

            if (!isUserNameValid(username))
                loginUIState.value.errors!!.usernameError = R.string.email_is_not_valid
            else
                loginUIState.value.errors!!.usernameError = null

            loginUIState.value.errors!!.communicationError = R.string.failed_to_log_in

            loginUIState.value = UiState(
                loading = false,
                data = null,
                errors = loginUIState.value.errors
            )
        } else {
            launch {
                when (val result =
                    repository.register(login = username, password = password, name = "")) {
                    is CommunicationResult.CommunicationError -> {
                        loginUIState.value = UiState(
                            loading = false,
                            data = null,
                            errors = LoginErrors(R.string.no_internet_connection, showError = true)
                        )
                    }

                    is CommunicationResult.Error -> {
                        loginUIState.value = UiState(
                            loading = false,
                            data = null,
                            errors = LoginErrors(R.string.failed_to_log_in, showError = true)
                        )
                    }

                    is CommunicationResult.Exception -> {
                        loginUIState.value = UiState(
                            loading = false,
                            data = null,
                            errors = LoginErrors(R.string.unknown_error, showError = true)
                        )
                    }

                    is CommunicationResult.Success -> {
                        loginUIState.value = UiState(
                            loading = true,
                            data = result.data,
                            errors = null
                        )
                        dataStoreRepo.setUserToken(result.data.token)
                    }
                }
            }
        }
    }

    fun onPasswordChanged(password: String?) {
        data.password = password ?: ""
        updateState()
    }

    override fun onUsernameChanged(username: String?) {
        data.username = username ?: ""
        updateState()
    }

    override fun onErrorDismiss() {
        loginUIState.value.errors!!.showError = false
        updateState()
    }

    fun updateState() {
        loginUIState.value = UiState(
            loading = loginUIState.value.loading,
            data = loginUIState.value.data,
            errors = loginUIState.value.errors
        )
    }
}

