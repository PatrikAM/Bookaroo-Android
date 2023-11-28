package cz.mendelu.pef.xmichl.bookaroo.ui.screens.login

import android.util.Patterns
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cz.mendelu.pef.xmichl.bookaroo.R
import cz.mendelu.pef.xmichl.bookaroo.architecture.BaseViewModel
import cz.mendelu.pef.xmichl.bookaroo.architecture.CommunicationResult
import cz.mendelu.pef.xmichl.bookaroo.communication.reader.IReaderRemoteRepository
import cz.mendelu.pef.xmichl.bookaroo.model.BookarooApiResponse
import cz.mendelu.pef.xmichl.bookaroo.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInUpViewModel
    @Inject constructor(
        private val repository: IReaderRemoteRepository,
//    private val dataStoreRepo: DataStoreRepositoryImpl
) : BaseViewModel(), LoginScreenActions {

    init {
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

    val loginUIState: MutableState<UiState<BookarooApiResponse, LoginErrors>> =
        mutableStateOf(UiState())

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
                            errors = LoginErrors(R.string.no_internet_connection)
                        )
                    }

                    is CommunicationResult.Error -> {
                        loginUIState.value = UiState(
                            loading = false,
                            data = null,
                            errors = LoginErrors(R.string.failed_to_log_in)
                        )
                    }

                    is CommunicationResult.Exception -> {
                        loginUIState.value = UiState(
                            loading = false,
                            data = null,
                            errors = LoginErrors(R.string.unknown_error)
                        )
                    }

                    is CommunicationResult.Success -> {
                        loginUIState.value = UiState(
                            loading = true,
                            data = result.data,
                            errors = null
                        )
//                        dataStoreRepo.setLoginSuccessful()
                    }
                }
            }
        }
    }

    override fun logout() {
        launch {
            when (val result = repository.logout()) {
                is CommunicationResult.CommunicationError -> {
                    loginUIState.value = UiState(
                        loading = false,
                        data = null,
                        errors = LoginErrors(R.string.no_internet_connection)
                    )
                }

                is CommunicationResult.Error -> {
                    loginUIState.value = UiState(
                        loading = false,
                        data = null,
                        errors = LoginErrors(R.string.failed_to_log_in)
                    )
                }

                is CommunicationResult.Exception -> {
                    loginUIState.value = UiState(
                        loading = false,
                        data = null,
                        errors = LoginErrors(R.string.unknown_error)
                    )
                }

                is CommunicationResult.Success -> {
                    loginUIState.value = UiState(
                        loading = false,
                        data = result.data,
                        errors = null
                    )
//                    dataStoreRepo.setLogoutSuccessful()
                }
            }
        }
    }

    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains("@")) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            false
        }
    }

    private fun isPasswordValid(password: String): Boolean {
//        TODO: check password for special chars and numbers
        return password.length > 6
    }
}

