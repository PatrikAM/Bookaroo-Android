package cz.mendelu.pef.xmichl.bookaroo.ui.screens.listOfBooks

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
import cz.mendelu.pef.xmichl.bookaroo.model.Book
import cz.mendelu.pef.xmichl.bookaroo.model.UiState
import cz.mendelu.pef.xmichl.bookaroo.ui.elements.BaseScreen
import cz.mendelu.pef.xmichl.bookaroo.ui.elements.BookarooSmallCard
import cz.mendelu.pef.xmichl.bookaroo.ui.elements.PlaceholderScreenContent
import cz.mendelu.pef.xmichl.bookaroo.ui.screens.destinations.AddEditLibraryScreenDestination
import cz.mendelu.pef.xmichl.bookaroo.ui.screens.destinations.BookAddEditScreenDestination
import cz.mendelu.pef.xmichl.bookaroo.ui.screens.destinations.BookDetailScreenDestination
import cz.mendelu.pef.xmichl.bookaroo.ui.screens.destinations.ListOfBooksScreenDestination
import cz.mendelu.pef.xmichl.bookaroo.ui.theme.getTintColor

@Destination(route = "books")
@Composable
fun ListOfBooksScreen(
    navigator: DestinationsNavigator,
    libraryId: String? = null
) {
    val viewModel = hiltViewModel<ListOfBooksViewModel>()

    val uiState: MutableState<UiState<List<Book>, ListOfBooksErrors>> =
        rememberSaveable {
            mutableStateOf(UiState())
        }

    viewModel.uiState.value.let {
        uiState.value = it
    }

    BaseScreen(
        topBarText = stringResource(R.string.books),
        onBackClick =
        if (libraryId != null) {
            { navigator.navigateUp() }
        } else null,
        drawFullScreenContent = true,
        showLoading = uiState.value.loading,
        placeholderScreenContent = if (uiState.value.errors != null &&
            !uiState.value.loading) {
            PlaceholderScreenContent(
                image = R.drawable.bookaroo,
                text = stringResource(id = uiState.value.errors!!.communicationError)
            )
        } else if (
            uiState.value.data != null &&
            uiState.value.data!!.isEmpty() &&
            !uiState.value.loading
        ) {
            PlaceholderScreenContent(
                image = R.drawable.ic_book_lover,
                text = stringResource(id = R.string.there_are_no_books)
            )
        } else null,
        actions = {
            IconButton(onClick = {
                viewModel.refreshBooks()
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
        currentRoute = ListOfBooksScreenDestination.route,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navigator.navigate(BookAddEditScreenDestination())
            }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = null,
                    tint = getTintColor()
                )
            }
        },
        isNavScreen = libraryId == null,

        ) {
        ListOfBooksScreenContent(
            paddingValues = it,
            uiState = uiState.value,
            onRowClick = {
                navigator.navigate(BookDetailScreenDestination(it))
            }
        )
    }
}

@Composable
fun ListOfBooksScreenContent(
    paddingValues: PaddingValues,
    uiState: UiState<List<Book>, ListOfBooksErrors>,
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
                BookarooSmallCard(
                    title = it.title ?: "",
                    subtitle = it.author,
                    modifier = Modifier
                        .size(width = 320.dp, height = 170.dp)
                        .fillParentMaxWidth(0.9f)
                        .padding(all = 10.dp),
//                    imageHeight = 120.dp,
//                    imageWidth = 100.dp,
                    photo = it.cover ?: "",
                    onCardClick = {
                        onRowClick(it.id!!)
                    }
                )
            }
        }
    }
}
