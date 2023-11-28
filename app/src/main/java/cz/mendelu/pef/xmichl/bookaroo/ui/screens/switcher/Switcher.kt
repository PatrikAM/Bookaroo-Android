package cz.mendelu.pef.xmichl.bookaroo.ui.screens.switcher

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import cz.mendelu.pef.xmichl.bookaroo.ui.screens.destinations.ListOfLibrariesScreenDestination
import cz.mendelu.pef.xmichl.bookaroo.ui.screens.destinations.LoginScreenDestination


@Destination(start = true, route = "switcher")
@Composable
fun Switcher(
    navigator: DestinationsNavigator
) {
    // TODO: check for user logged in
    // TODO: show login screen
    // TODO: download all necessary data, show splash with loading
    navigator.navigate(LoginScreenDestination())
//    navigator.navigate(ListOfLibrariesScreenDestination())
}