package cz.mendelu.pef.xmichl.bookaroo.ui.screens.bookDetail

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.utils.route
import cz.mendelu.pef.xmichl.bookaroo.R
import cz.mendelu.pef.xmichl.bookaroo.model.Book
import cz.mendelu.pef.xmichl.bookaroo.model.UiState
import cz.mendelu.pef.xmichl.bookaroo.testTags.BooksTestTags
import cz.mendelu.pef.xmichl.bookaroo.ui.elements.BaseScreen
import cz.mendelu.pef.xmichl.bookaroo.ui.elements.DetailItem
import cz.mendelu.pef.xmichl.bookaroo.ui.elements.HorizontalLine
import cz.mendelu.pef.xmichl.bookaroo.ui.elements.ImageOnBlurredImage
import cz.mendelu.pef.xmichl.bookaroo.ui.elements.PlaceholderScreenContent
import cz.mendelu.pef.xmichl.bookaroo.ui.screens.destinations.BookAddEditScreenDestination
import cz.mendelu.pef.xmichl.bookaroo.ui.screens.destinations.ListOfBooksScreenDestination
import cz.mendelu.pef.xmichl.bookaroo.ui.theme.getTintAltColor
import cz.mendelu.pef.xmichl.bookaroo.ui.theme.getTintColor
import cz.mendelu.pef.xmichl.bookaroo.ui.theme.headLine
import cz.mendelu.pef.xmichl.bookaroo.ui.theme.smallMargin

@Destination(route = "bookDetail")
@Composable
fun BookDetailScreen(
    navController: NavController,
    navigator: DestinationsNavigator,
    bookId: String,
    ) {

    val viewModel = hiltViewModel<BookDetailViewModel>()

    val uiState: MutableState<UiState<Book, BookErrors>> =
        rememberSaveable {
            mutableStateOf(UiState())
        }

    viewModel.uiState.value.let {
        uiState.value = it
    }

    if (uiState.value.actionDone) {
        LaunchedEffect(uiState.value.actionDone) {
            navController.previousBackStackEntry?.savedStateHandle?.set("reload", true)
            navigator.popBackStack()
        }
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
            IconButton(onClick = {
                viewModel.deleteBook()
            }) {
                Icon(
                    imageVector = Icons.Rounded.Delete,
                    contentDescription = null,
                    tint = getTintColor()
                )
            }
            IconButton(
                modifier = Modifier.testTag(BooksTestTags.TestTagBookEditButton),
                onClick = {
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
            data = uiState.value.data!!,
            actions = viewModel
        )
    }
}

@Composable
fun BookDetailScreenContent(
    paddingValues: PaddingValues,
    data: Book,
    actions: BookActions
) {
    val context = LocalContext.current

    Column(
        Modifier
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {

        Text(
            data.title ?: stringResource(R.string.unknown),
            style = headLine(),
            modifier = Modifier.testTag(BooksTestTags.TestTagBookTitle)
        )
        Text(
            data.subtitle ?: "",
            Modifier.padding(smallMargin())
        )
        Text(
            data.author
                ?: (stringResource(R.string.unknown)
                        + " "
                        + stringResource(R.string.author)),
            modifier = Modifier.testTag(BooksTestTags.TestTagBookAuthor)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.width(200.dp)) {
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth(0.9F)
                        .fillMaxHeight(0.9F)
//                    .fillMaxWidth(0.5F)
//                    .fillMaxHeight(0.5F)
//                    .fillMaxHeight()
                ) {
                    Box(
                        modifier = Modifier,
//                            .fillMaxWidth(0.5F),
                        contentAlignment = Alignment.Center
                    ) {

                        ImageOnBlurredImage(imageUrl = data.cover)
                    }
                }
            }



            Column(
                modifier = Modifier.width(200.dp)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.9F)
                        .fillMaxHeight(0.9F)
                ) {
                    Column {
                        Text(text = "ISBN", color = getTintAltColor())
                        data.isbn?.let {
                            Text(
                                text = it,
                                modifier = Modifier.testTag(BooksTestTags.TestTagBookISBN)
                            )
                        }
                    }

                    Spacer(Modifier.size(smallMargin()))

                    Column {
                        Text(
                            text = stringResource(R.string.page_count),
                            color = getTintAltColor()
                        )
                        Text(text = (data.pages ?: 0).toString())
                    }

                    Spacer(Modifier.size(smallMargin()))

                    Column {
                        Text(
                            text = stringResource(R.string.published),
                            color = getTintAltColor()
                        )
                        Text(text = (data.published ?: 0).toString())
                    }
                }
            }
        }

//        HorizontalLine()

        Column {
            data.publisher?.let {
                DetailItem(
                    key = stringResource(R.string.publisher),
                    value = it
                ) { key, value ->
                    actions.insertToClipboard(key, value, context = context)
                }
            }

            data.language?.let {
                DetailItem(
                    key = stringResource(R.string.language),
                    value = it
                ) { key, value ->
                    actions.insertToClipboard(key, value, context = context)
                }
                Text(text = it, color = getTintColor())
            }

            data.description?.let {
                Text(text = it, color = getTintColor())
            }
        }

    }
}
