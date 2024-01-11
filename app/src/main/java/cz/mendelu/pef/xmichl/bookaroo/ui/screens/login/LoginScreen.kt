package cz.mendelu.pef.xmichl.bookaroo.ui.screens.login

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import cz.mendelu.pef.xmichl.bookaroo.R
import cz.mendelu.pef.xmichl.bookaroo.model.BookarooApiResponse
import cz.mendelu.pef.xmichl.bookaroo.model.Reader
import cz.mendelu.pef.xmichl.bookaroo.model.UiState
import cz.mendelu.pef.xmichl.bookaroo.ui.elements.BaseScreen
import cz.mendelu.pef.xmichl.bookaroo.ui.elements.SingInUpScreenContent
import cz.mendelu.pef.xmichl.bookaroo.ui.screens.destinations.ListOfLibrariesScreenDestination
import cz.mendelu.pef.xmichl.bookaroo.ui.screens.destinations.SignUpScreenDestination
import cz.mendelu.pef.xmichl.bookaroo.ui.theme.getTintColor

@Destination(route = "login")
@Composable
fun LoginScreen(
    navigator: DestinationsNavigator
) {

    val viewModel = hiltViewModel<SignInUpViewModel>()

    val data: LoginData by remember {
        mutableStateOf(viewModel.data)
    }

    val uiState: MutableState<UiState<Reader, LoginErrors>> =
        rememberSaveable {
            mutableStateOf(UiState())
        }

    viewModel.loginUIState.value.let {
        uiState.value = it
    }

    if (uiState.value.data != null)
        navigator.navigate(ListOfLibrariesScreenDestination())

    BaseScreen(
        topBarText = null, drawFullScreenContent = false
    ) {
        LoginScreenContent(
            onSignUpClick = { navigator.navigate(SignUpScreenDestination()) },
            onSignInClick = {
                            viewModel.login(
                                username = data.username,
                                password = data.password
                            )
            },
            actions = viewModel,
            data = data,
            errors = uiState.value.errors,
            isLoading = uiState.value.loading
        )
    }
}

@Composable
fun LoginScreenContent(
    onSignUpClick: () -> Unit,
    onSignInClick: () -> Unit,
    data: LoginData,
    actions: SignInUpViewModel,
    errors: LoginErrors?,
    isLoading: Boolean
) {
    SingInUpScreenContent(
        onSecondaryButtonClick = onSignUpClick,
        onPrimaryButtonClick = onSignInClick,
        primaryButtonText = {
            Icon(
                imageVector = Icons.Filled.Lock,
                contentDescription = null,
                tint = getTintColor()
            )
            Text(stringResource(R.string.log_in), color = getTintColor())
        },
        secondaryButtonText = {
            Text(stringResource(R.string.sign_up))
        },
        hintText = stringResource(R.string.don_t_have_an_account),
        actions = actions,
        data = data,
        errors = errors,
        isLoading = isLoading
    )
}
