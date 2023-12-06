package cz.mendelu.pef.xmichl.bookaroo.ui.screens.listOfLibraries

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import cz.mendelu.pef.xmichl.bookaroo.R
import cz.mendelu.pef.xmichl.bookaroo.model.Library
import cz.mendelu.pef.xmichl.bookaroo.model.UiState
import cz.mendelu.pef.xmichl.bookaroo.ui.elements.BaseScreen
import cz.mendelu.pef.xmichl.bookaroo.ui.elements.BookarooBigCard
import cz.mendelu.pef.xmichl.bookaroo.ui.elements.PlaceholderScreenContent
import cz.mendelu.pef.xmichl.bookaroo.ui.screens.destinations.AddEditLibraryScreenDestination
import cz.mendelu.pef.xmichl.bookaroo.ui.screens.destinations.ListOfBooksScreenDestination
import cz.mendelu.pef.xmichl.bookaroo.ui.screens.destinations.ListOfLibrariesScreenDestination
import cz.mendelu.pef.xmichl.bookaroo.ui.theme.getTintColor

@Destination(route = "libraries")
@Composable
fun ListOfLibrariesScreen(
    navigator: DestinationsNavigator
) {
    val viewModel = hiltViewModel<ListOfLibrariesViewModel>()

    val uiState: MutableState<UiState<List<Library>, ListOfLibrariesErrors>> =
        rememberSaveable {
            mutableStateOf(UiState())
        }


    viewModel.uiState.value.let {
        uiState.value = it
    }

//    uiState.value.data = libraryList


    BaseScreen(
        topBarText = stringResource(R.string.libraries),
        drawFullScreenContent = true,
        showLoading = viewModel.uiState.value.loading,
        placeholderScreenContent = if (uiState.value.errors != null &&
            !uiState.value.loading) {
            PlaceholderScreenContent(
                image = uiState.value.image ?: R.drawable.bookaroo,
                text = stringResource(id = uiState.value.errors!!.communicationError)
            )
        } else if (
            uiState.value.data != null &&
            uiState.value.data!!.isEmpty() &&
            !uiState.value.loading
        ) {
            PlaceholderScreenContent(
                image = R.drawable.ic_bookshelves,
                text = stringResource(R.string.there_are_no_libraries_yet)
            )
        } else null,
        actions = {
            IconButton(onClick = {
                viewModel.refreshLibraries()
            }) {
                Icon(
                    imageVector = Icons.Rounded.Refresh,
                    contentDescription = null,
                    tint = getTintColor()
                )
            }
            IconButton(
                onClick = {
//                    viewModel.logout()
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Logout,
                    contentDescription = null,
                    tint = getTintColor()
                )
            }
        },

        navigator = navigator,
        currentRoute = ListOfLibrariesScreenDestination.route,
//        floatingActionButton = {
//            FloatingActionButton(onClick = {
//                navigator.navigate(AddEditLibraryScreenDestination())
//            }) {
//                Icon(
//                    imageVector = Icons.Filled.Add,
//                    contentDescription = null,
//                    tint = getTintColor()
//                )
//            }
//        },
        isNavScreen = true,

        ) {
        ListOfLibrariesScreenContent(
            paddingValues = it,
            uiState = uiState.value,
            onRowClick = { libId ->
                navigator.navigate(ListOfBooksScreenDestination(libId))
            }
        )
    }
}

@Composable
fun ListOfLibrariesScreenContent(
    paddingValues: PaddingValues,
    uiState: UiState<List<Library>, ListOfLibrariesErrors>,
    onRowClick: (id: String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        uiState.data?.forEach {

            item {
                BookarooBigCard(
                    title = it.name ?: "",
                    subtitle = "",
//                    subtitle = it.ownerId.toString() ?: "",
                    modifier = Modifier
                        .size(width = 320.dp, height = 170.dp)
                        .fillParentMaxWidth(0.9f)
                        .padding(all = 10.dp),
//                    imageHeight = 120.dp,
//                    imageWidth = 100.dp,
                    slots = mapOf(
                        stringResource(R.string.total) to it.total.toString(),
                        stringResource(R.string.favourite) to it.favourite.toString(),
                        stringResource(R.string.read) to it.read.toString()
                    ),
                    onCardClick = {
                        onRowClick(it.id)
                    }
                )
            }
        }
    }
}
