package cz.mendelu.pef.xmichl.bookaroo.ui.screens.root

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import cz.mendelu.pef.xmichl.bookaroo.model.UiState
import cz.mendelu.pef.xmichl.bookaroo.ui.screens.destinations.ListOfLibrariesScreenDestination
import cz.mendelu.pef.xmichl.bookaroo.ui.screens.destinations.LoginScreenDestination
import cz.mendelu.pef.xmichl.bookaroo.ui.screens.login.LoginErrors


@Destination(start = true, route = "switcher")
@Composable
fun BookarooRoot(
    navigator: DestinationsNavigator
) {

    val viewModel = hiltViewModel<BookarooRootViewModel>()

//    var data: BookarooRootData by remember {
//        mutableStateOf(viewModel.data)
//    }

    val uiState: MutableState<UiState<String, LoginErrors>> =
        rememberSaveable {
            mutableStateOf(UiState())
        }

    viewModel.uiState.value.let {
        uiState.value = it
    }

    if (!uiState.value.loading) {
        if (uiState.value.data != null) {
            navigator.navigate(ListOfLibrariesScreenDestination())
        } else {
            navigator.navigate(LoginScreenDestination())
        }
    }

    // TODO: check for user logged in
    // TODO: show login screen
//    navigator.navigate(LoginScreenDestination())
    // TODO: download all necessary data, show splash with loading

//    navigator.navigate(ListOfLibrariesScreenDestination())
}