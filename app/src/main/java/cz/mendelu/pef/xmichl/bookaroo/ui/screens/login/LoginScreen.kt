package cz.mendelu.pef.xmichl.bookaroo.ui.screens.login

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import cz.mendelu.pef.xmichl.bookaroo.R
import cz.mendelu.pef.xmichl.bookaroo.ui.elements.BaseScreen
import cz.mendelu.pef.xmichl.bookaroo.ui.elements.SingInUpScreenContent
import cz.mendelu.pef.xmichl.bookaroo.ui.screens.destinations.SignUpScreenDestination
import cz.mendelu.pef.xmichl.bookaroo.ui.theme.getTintColor


@Destination(route = "login")
@Composable
fun LoginScreen(
    navigator: DestinationsNavigator
) {
    BaseScreen(
        topBarText = null, drawFullScreenContent = false
    ) {
        LoginScreenContent(
            onSignUpClick = { navigator.navigate(SignUpScreenDestination()) }
        )
    }
}

@Composable
fun LoginScreenContent(
    onSignUpClick: () -> Unit,
) {
    SingInUpScreenContent(
        onSecondaryButtonClick = onSignUpClick,
        onPrimaryButtonClick = {},
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
        hintText = stringResource(R.string.don_t_have_an_account)
    )
}
