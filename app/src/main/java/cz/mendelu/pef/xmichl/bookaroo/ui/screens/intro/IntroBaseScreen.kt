package cz.mendelu.pef.xmichl.bookaroo.ui.screens.intro

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import cz.mendelu.pef.xmichl.bookaroo.R
import cz.mendelu.pef.xmichl.bookaroo.ui.elements.BaseScreen

@Composable
fun IntroBaseScreen(
    navigator: DestinationsNavigator,
    nextDestination: String,
    content: @Composable () -> Unit
) {
    BaseScreen(
        topBarText = null,
        drawFullScreenContent = false
    ) {
        IntroBaseScreenContent(navigator, nextDestination) {
            content()
        }
    }
}

@Composable
fun IntroBaseScreenContent(
    navigator: DestinationsNavigator,
    nextDestination: String,
    content: @Composable () -> Unit
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

        Row {
            Button(onClick = { navigator.popBackStack() }) {
                Text("< Back")
            }
            Button(onClick = { /*TODO*/ }) {
                Text("Next >")
            }
        }
        Button(onClick = {
//            navigator.navigate(SwitcherDestination())
        }) {
            Text("Skip")
        }
    }

}
