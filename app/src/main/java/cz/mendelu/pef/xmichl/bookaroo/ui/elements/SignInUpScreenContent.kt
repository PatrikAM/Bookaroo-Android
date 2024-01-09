package cz.mendelu.pef.xmichl.bookaroo.ui.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cz.mendelu.pef.xmichl.bookaroo.R
import cz.mendelu.pef.xmichl.bookaroo.ui.screens.login.LoginData
import cz.mendelu.pef.xmichl.bookaroo.ui.screens.login.LoginErrors
import cz.mendelu.pef.xmichl.bookaroo.ui.screens.login.SignInUpViewModel
import cz.mendelu.pef.xmichl.bookaroo.ui.theme.Purple40
import cz.mendelu.pef.xmichl.bookaroo.ui.theme.bookarooPrimaryColor
import cz.mendelu.pef.xmichl.bookaroo.ui.theme.getTintAltColor

@Composable
fun SingInUpScreenContent(
    onSecondaryButtonClick: () -> Unit,
    onPrimaryButtonClick: () -> Unit,
    primaryButtonText: @Composable () -> Unit,
    secondaryButtonText: @Composable () -> Unit,
    hintText: String,
    data: LoginData,
    actions: SignInUpViewModel,
    errors: LoginErrors?,
    isLoading: Boolean = false
) {

    if (errors != null && errors.showError) {
        BookarooDialog(
            content = PlaceholderScreenContent(
                null,
                stringResource(id = errors.communicationError!!)
            )
        ) {
            actions.onErrorDismiss()
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painterResource(R.drawable.bookaroo),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(0.5F)
                .padding(top = 10.dp, bottom = 20.dp)
        )

        MyTextfield(
            value = data.username,
            singleLine = true,
            onValueChange = {
                actions.onUsernameChanged(it)
            },
            leadingIcon = Icons.Outlined.AccountCircle,
            label = stringResource(R.string.email),
            onClearClick = {
                actions.onUsernameChanged(null)
            },
            textError = errors?.usernameError
        )

        MyTextfield(
            value = data.password,
            onValueChange = {
                actions.onPasswordChanged(it)
            },
            leadingIcon = Icons.Filled.Password,
            label = stringResource(R.string.password),
            onClearClick = {
                actions.onPasswordChanged(null)
            },
            isSecret = true,
            textError = errors?.passwordError
        )

        Button(
            modifier = Modifier
                .fillMaxWidth(0.8F),
            enabled = !isLoading,
            colors = ButtonDefaults.buttonColors(containerColor = bookarooPrimaryColor),
            onClick = onPrimaryButtonClick
        ) {
            Row {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(48.dp),
                        color = Purple40,
                        strokeWidth = 5.dp
                    )
                }
                primaryButtonText()
            }
        }
        Text(hintText, color = getTintAltColor())
        OutlinedButton(
            onClick = onSecondaryButtonClick,
            enabled = !isLoading,
        ) {
//            Text("Sign Up")
            secondaryButtonText()
        }
    }
}
