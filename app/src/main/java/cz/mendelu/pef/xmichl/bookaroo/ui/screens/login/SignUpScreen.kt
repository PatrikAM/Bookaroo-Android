package cz.mendelu.pef.xmichl.bookaroo.ui.screens.login

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HowToReg
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import cz.mendelu.pef.xmichl.bookaroo.ui.screens.destinations.LoginScreenDestination
import cz.mendelu.pef.xmichl.bookaroo.ui.theme.getTintColor

@Destination(route = "signup")
@Composable
fun SignUpScreen(
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

    if (uiState.value.data != null) {
//        navigator.navigate(IntroRootDestination())
    }

    BaseScreen(
        topBarText = null,
        drawFullScreenContent = false
    ) {
        SignUpScreenContent(
            onSignUpClick={
                viewModel.register(
                    name = "",
                    username = data.username,
                    password = data.password
                )
            },
            onSignInClick={navigator.navigate(LoginScreenDestination())},
            actions = viewModel,
            data = data,
            errors = uiState.value.errors
        )
    }
}

@Composable
fun SignUpScreenContent(
    onSignUpClick: () -> Unit,
    onSignInClick: () -> Unit,
    data: LoginData,
    actions: SignInUpViewModel,
    errors: LoginErrors?
) {
    SingInUpScreenContent(
        onSecondaryButtonClick = onSignInClick,
        onPrimaryButtonClick = onSignUpClick,
        secondaryButtonText = {
            Text(stringResource(R.string.log_in), color = getTintColor())
        },
        primaryButtonText = {
            Icon(
                imageVector = Icons.Filled.HowToReg,
                contentDescription = null,
                tint = getTintColor()
            )
            Text(stringResource(R.string.sign_up), color = getTintColor())
        },
        hintText = stringResource(R.string.already_have_an_account),
        actions = actions,
        data = data,
        errors = errors
    )
}
