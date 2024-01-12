package cz.mendelu.pef.xmichl.bookaroo.ui.screens.root

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import cz.mendelu.pef.xmichl.bookaroo.R
import cz.mendelu.pef.xmichl.bookaroo.model.UiState
import cz.mendelu.pef.xmichl.bookaroo.ui.elements.BaseScreen
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
    } else {
//        BaseScreen(topBarText = "", isNavScreen = false) {
//            Image(
//                painterResource(id = R.drawable.bookaroo),
//                contentDescription = null
//            )
//        }
    }
}