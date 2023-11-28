package cz.mendelu.pef.xmichl.bookaroo.ui.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cz.mendelu.pef.xmichl.bookaroo.R
import cz.mendelu.pef.xmichl.bookaroo.ui.theme.bookarooPrimaryColor
import cz.mendelu.pef.xmichl.bookaroo.ui.theme.getTintAltColor
import cz.mendelu.pef.xmichl.bookaroo.ui.theme.getTintColor

@Composable
fun SingInUpScreenContent(
    onSecondaryButtonClick: () -> Unit,
    onPrimaryButtonClick: () -> Unit,
    primaryButtonText: @Composable () -> Unit,
    secondaryButtonText: @Composable () -> Unit,
    hintText: String
) {

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
            value = "",
            singleLine = true,
            onValueChange = {},
            leadingIcon = Icons.Outlined.AccountCircle,
            label = stringResource(R.string.email),
            onClearClick = { /*TODO*/ }
        )

        MyTextfield(
            value = "",
            onValueChange = {},
            leadingIcon = Icons.Filled.Password,
            label = stringResource(R.string.password),
            onClearClick = { /*TODO*/ }
        )

        Button(modifier = Modifier.fillMaxWidth(0.8F),
            colors = ButtonDefaults.buttonColors(containerColor = bookarooPrimaryColor),
            onClick = { /*TODO*/ }) {
            primaryButtonText()
        }
        Text(hintText, color = getTintAltColor())
        OutlinedButton(onClick = onSecondaryButtonClick) {
//            Text("Sign Up")
            secondaryButtonText()
        }
    }
}
