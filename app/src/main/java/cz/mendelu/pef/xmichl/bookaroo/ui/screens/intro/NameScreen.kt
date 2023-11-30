package cz.mendelu.pef.xmichl.bookaroo.ui.screens.intro

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(route = "name")
@Composable
fun NameScreen(
    navigator: DestinationsNavigator
) {
    IntroBaseScreen(
        nextDestination = "switcher",
        navigator = navigator
    ) {

    }
}
