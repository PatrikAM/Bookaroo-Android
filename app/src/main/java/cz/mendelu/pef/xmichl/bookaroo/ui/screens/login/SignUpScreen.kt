package cz.mendelu.pef.xmichl.bookaroo.ui.screens.login

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HowToReg
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import cz.mendelu.pef.xmichl.bookaroo.R
import cz.mendelu.pef.xmichl.bookaroo.ui.elements.BaseScreen
import cz.mendelu.pef.xmichl.bookaroo.ui.elements.SingInUpScreenContent
import cz.mendelu.pef.xmichl.bookaroo.ui.screens.destinations.LoginScreenDestination
import cz.mendelu.pef.xmichl.bookaroo.ui.theme.getTintColor

@Destination(route = "signup")
@Composable
fun SignUpScreen(
    navigator: DestinationsNavigator
) {
    BaseScreen(
        topBarText = null,
        drawFullScreenContent = false
    ) {
        SignUpScreenContent(
            onSignUpClick={},
            onSignInClick={navigator.navigate(LoginScreenDestination())},
        )
    }
}

@Composable
fun SignUpScreenContent(
    onSignUpClick: () -> Unit,
    onSignInClick: () -> Unit
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
        hintText = stringResource(R.string.already_have_an_account)
    )
}
