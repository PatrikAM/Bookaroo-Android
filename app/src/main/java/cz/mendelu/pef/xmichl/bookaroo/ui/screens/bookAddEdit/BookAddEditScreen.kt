package cz.mendelu.pef.xmichl.bookaroo.ui.screens.bookAddEdit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.rounded.Save
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import cz.mendelu.pef.xmichl.bookaroo.R
import cz.mendelu.pef.xmichl.bookaroo.model.Book
import cz.mendelu.pef.xmichl.bookaroo.model.Library
import cz.mendelu.pef.xmichl.bookaroo.model.UiState
import cz.mendelu.pef.xmichl.bookaroo.testTags.BooksTestTags
import cz.mendelu.pef.xmichl.bookaroo.ui.elements.BaseScreen
import cz.mendelu.pef.xmichl.bookaroo.ui.elements.BookarooDialog
import cz.mendelu.pef.xmichl.bookaroo.ui.elements.MyTextfield
import cz.mendelu.pef.xmichl.bookaroo.ui.elements.PlaceholderScreenContent
import cz.mendelu.pef.xmichl.bookaroo.ui.elements.SelectItemElement
import cz.mendelu.pef.xmichl.bookaroo.ui.screens.bookDetail.BookErrors
import cz.mendelu.pef.xmichl.bookaroo.ui.screens.destinations.BookAddEditScreenDestination
import cz.mendelu.pef.xmichl.bookaroo.ui.theme.getTintAltColor
import cz.mendelu.pef.xmichl.bookaroo.ui.theme.getTintColor

@Destination(route = "bookAddEdit")
@Composable
fun BookAddEditScreen(
    navController: NavController,
    navigator: DestinationsNavigator,
    bookId: String? = null,
    libraryId: String? = null,
    isbn: String? = null
) {

    val viewModel = hiltViewModel<BookAddEditViewModel>()

    val uiState: MutableState<UiState<Book, BookErrors>> =
        rememberSaveable {
            mutableStateOf(UiState())
        }

    val uiLibState: MutableState<UiState<List<Library>, LibraryErrors>> =
        rememberSaveable {
            mutableStateOf(UiState())
        }

    viewModel.uiState.value.let {
        uiState.value = it
    }

    viewModel.uiLibState.value.let {
        uiLibState.value = it
    }

    if (uiState.value.actionDone) {
        LaunchedEffect(uiState.value.actionDone) {
            navController.previousBackStackEntry?.savedStateHandle?.set("reload", true)
            navigator.popBackStack()
        }
    }

    viewModel.responseError
        ?.let { stringResource(id = it) }
        ?.let {
            if (viewModel.showDialog) {
                BookarooDialog(
                    modifierDismissButton = Modifier.testTag(BooksTestTags.TestTagBookDismissDialogButton),
                    modifier = Modifier.testTag(BooksTestTags.TestTagBookFillFormDialog),
                    content = PlaceholderScreenContent(
                        image = viewModel.image,
                        text = if (viewModel.responseError != null) {
                            stringResource(id = viewModel.responseError!!)
                        } else null
                    )
                ) {
                    viewModel.onDialogDismiss()
                }
            }
        }

    BaseScreen(
        topBarText = "",
        onBackClick = {
            navController.previousBackStackEntry?.savedStateHandle?.set("reload", false)
            navigator.popBackStack()
        },
        drawFullScreenContent = true,
        showLoading = uiState.value.loading,
        actions = {
            Button(
                modifier = Modifier.testTag(BooksTestTags.TestTagBookSaveButton),
                enabled = !viewModel.uiState.value.loading,
//                || (bookId == null && isbn == null),
                onClick = {
                viewModel.saveBook()
            }) {
                Icon(
                    imageVector = Icons.Rounded.Save,
                    contentDescription = null,
                    tint = getTintColor()
                )
                Text(text = stringResource(R.string.save))
            }
        },

        navigator = navigator,
        currentRoute = BookAddEditScreenDestination.route,
        isNavScreen = false,

        ) {
        BookAddEditScreenContent(
            paddingValues = it,
            data = uiState.value.data!!,
            actions = viewModel,
            libraries = uiLibState.value.data ?: listOf(),
            isLibraryCreationDone = !uiLibState.value.loading,
            valueErrors = viewModel.bookErrors,
//            responseError = viewModel.responseError,
//            placeholderScreenContent = PlaceholderScreenContent(
//                image = viewModel.image,
//                text = if (viewModel.responseError != null) {
//                    stringResource(id = viewModel.responseError!!)
//                } else null
//            ),
//            showDialog = viewModel.showDialog
        )
    }
}

@Composable
fun BookAddEditScreenContent(
    paddingValues: PaddingValues,
    data: Book,
    libraries: List<Library>,
    actions: BookAddEditActions,
    isLibraryCreationDone: Boolean,
    valueErrors: BookValueErrors,
//    responseError: Int? = null,
//    placeholderScreenContent: PlaceholderScreenContent,
//    showDialog: Boolean
) {

    val isAdvancedOpened = rememberSaveable {
        mutableStateOf(false)
    }

    Column(
        Modifier
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
            .testTag(BooksTestTags.TestTagBookForm),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MyTextfield(
            value = data.title ?: "",
            onValueChange = { actions.onTitleChanged(it) },
            leadingIcon = null,
            label = "* " + stringResource(R.string.title),
            onClearClick = { actions.onTitleChanged(null) },
            textError = valueErrors.titleError,
            isError = { it && data.title == null },
            modifier = Modifier.testTag(BooksTestTags.TestTagBookTitleTextField)
        )

        MyTextfield(
            value = data.author ?: "",
            onValueChange = { actions.onAuthorChanged(it) },
            leadingIcon = null,
            label = "* " + stringResource(id = R.string.author),
            onClearClick = { actions.onAuthorChanged(null) },
            textError = valueErrors.authorError,
            isError = { it && data.author == null },
            modifier = Modifier.testTag(BooksTestTags.TestTagBookAuthorTextField)
        )

        MyTextfield(
            value = data.isbn ?: "",
            onValueChange = { actions.onISBNChanged(it) },
            leadingIcon = null,
            label = "* " + "ISBN",
            onClearClick = { actions.onISBNChanged(null) },
            textError = valueErrors.isbnError,
            isError = { it && data.author == null },
            modifier = Modifier.testTag(BooksTestTags.TestTagBookISBNTextField)
        )

        SelectItemElement(
            label = "* " + stringResource(id = R.string.library),
            items = libraries.sortedByDescending { it.name },
            selectedId = data.library,
            value = libraries
                .filter { it.id == data.library }
                .let {
                    if (it.isNotEmpty()) {
                        it[0].name
                    } else null
                },
            onNewCreate = {
                actions.onNewLibrary(it)
            },
            onItemSelected = { id, item ->
                actions.onLibraryChanged(id)
            },
            isLibraryCreationDone = isLibraryCreationDone,
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { isAdvancedOpened.value = !isAdvancedOpened.value },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.advanced),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(10.dp)
            )
            Icon(
                imageVector =
                if (isAdvancedOpened.value)
                    Icons.Filled.ExpandMore
                else Icons.Filled.ExpandLess,
                contentDescription = null,
            )
        }
        if (isAdvancedOpened.value) {
            Divider(color = getTintAltColor(), thickness = 1.dp)

            MyTextfield(
                value = data.subtitle ?: "",
                onValueChange = { actions.onSubTitleChanged(it) },
                leadingIcon = null,
                label = stringResource(R.string.subtitle),
                onClearClick = { actions.onSubTitleChanged(null) }
            )

            MyTextfield(
                value = data.publisher ?: "",
                onValueChange = { actions.onPublisherChanged(it) },
                leadingIcon = null,
                label = "Publisher",
                onClearClick = { actions.onPublisherChanged(null) })

            MyTextfield(
                value = data.published ?: "",
                onValueChange = { actions.onPublishedChanged(it) },
                leadingIcon = null,
                label = stringResource(R.string.published),
                onClearClick = { actions.onPublishedChanged(null) }
            )

//            MyTextfield(
//                value = data.translator ?: "",
//                onValueChange = { },
//                leadingIcon = null,
//                label = stringResource(R.string.translator),
//                onClearClick = { })

            MyTextfield(
                value = data.pages?.toString() ?: "",
                onValueChange = { actions.onPagesChanged(it) },
                leadingIcon = null,
                label = stringResource(R.string.page_count),
                onClearClick = { actions.onPagesChanged(null) }
            )

            MyTextfield(
                value = data.cover ?: "",
                onValueChange = { actions.onCoverChanged(it) },
                leadingIcon = null,
                label = stringResource(R.string.cover),
                onClearClick = { actions.onCoverChanged(null) }
            )

        }


    }

}
