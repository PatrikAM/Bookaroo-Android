package cz.mendelu.pef.xmichl.bookaroo.ui.screens.bookDetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
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
import cz.mendelu.pef.xmichl.bookaroo.ui.elements.ImageOnBlurredImage
import cz.mendelu.pef.xmichl.bookaroo.ui.elements.PlaceholderScreenContent
import cz.mendelu.pef.xmichl.bookaroo.ui.screens.destinations.BookAddEditScreenDestination
import cz.mendelu.pef.xmichl.bookaroo.ui.screens.destinations.ListOfBooksScreenDestination
import cz.mendelu.pef.xmichl.bookaroo.ui.theme.getTintAltColor
import cz.mendelu.pef.xmichl.bookaroo.ui.theme.getTintColor
import cz.mendelu.pef.xmichl.bookaroo.ui.theme.headLine

@Destination(route = "bookDetail")
@Composable
fun BookDetailScreen(
    navigator: DestinationsNavigator,
    bookId: String
) {

    val viewModel = hiltViewModel<BookDetailViewModel>()

    val uiState: MutableState<UiState<Book, BookErrors>> =
        rememberSaveable {
            mutableStateOf(UiState())
        }

    viewModel.uiState.value.let {
        uiState.value = it
    }

    BaseScreen(
        topBarText = "",
        onBackClick = { navigator.navigateUp() },
        drawFullScreenContent = true,
        showLoading = uiState.value.loading,
        placeholderScreenContent = if (
            uiState.value.errors != null &&
            !uiState.value.loading
        ) {
            PlaceholderScreenContent(
                image = uiState.value.image ?: R.drawable.ic_book_lover,
                text = stringResource(id = uiState.value.errors!!.communicationError)
            )
        } else null,
        actions = {
            IconButton(onClick = {
                viewModel.refreshBook()
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
                    navigator.navigate(BookAddEditScreenDestination(bookId))
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = null,
                    tint = getTintColor()
                )
            }
        },

        navigator = navigator,
        currentRoute = ListOfBooksScreenDestination.route,
        isNavScreen = false,

        ) {
        BookDetailScreenContent(
            paddingValues = it,
            data = uiState.value.data!!
        )
    }
}

@Composable
fun BookDetailScreenContent(
    paddingValues: PaddingValues,
    data: Book,
) {
    Column(
        Modifier
            .padding(paddingValues), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            data.title ?: stringResource(R.string.unknown),
            style = headLine()
        )
        Text(data.subtitle ?: "")
        Text(
            data.author
                ?: (stringResource(R.string.unknown)
                        + " "
                        + stringResource(R.string.author))
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            ElevatedCard(
                modifier = Modifier
                    .fillMaxHeight()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5F),
                    contentAlignment = Alignment.Center
                ) {

                    ImageOnBlurredImage(imageUrl = data.cover)
                }
            }

//            ElevatedCard(
//                modifier = Modifier
//                    .fillMaxHeight()
//                    .fillMaxWidth()
//            ) {
            Column {
                Column {
                    Text(text = "ISBN", color = getTintAltColor())
                    data.isbn?.let { Text(text = it) }
                }
                Column {
                    Text(
                        text = stringResource(R.string.page_count),
                        color = getTintAltColor()
                    )
                    Text(text = (data.pageCount ?: 0).toString())
                }
//            }
            }
        }

        data.description?.let { Text(text = it) }

    }
}
