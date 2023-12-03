package cz.mendelu.pef.xmichl.bookaroo.ui.screens.bookAddEdit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import cz.mendelu.pef.xmichl.bookaroo.ui.elements.MyTextfield
import cz.mendelu.pef.xmichl.bookaroo.ui.elements.PlaceholderScreenContent
import cz.mendelu.pef.xmichl.bookaroo.ui.screens.bookDetail.BookErrors
import cz.mendelu.pef.xmichl.bookaroo.ui.screens.destinations.BookAddEditScreenDestination
import cz.mendelu.pef.xmichl.bookaroo.ui.theme.getTintAltColor
import cz.mendelu.pef.xmichl.bookaroo.ui.theme.getTintColor

@Destination(route = "bookAddEdit")
@Composable
fun BookAddEditScreen(
    navigator: DestinationsNavigator,
    bookId: String? = null
) {

    val viewModel = hiltViewModel<BookAddEditViewModel>()

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
        placeholderScreenContent = if (uiState.value.errors != null &&
            !uiState.value.loading
        ) {
            PlaceholderScreenContent(
                image = R.drawable.ic_book_lover,
                text = stringResource(id = uiState.value.errors!!.communicationError)
            )
        } else null,
        actions = {
            Button(onClick = {
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
            actions = viewModel
        )
    }
}

@Composable
fun BookAddEditScreenContent(
    paddingValues: PaddingValues,
    data: Book,
    actions: BookAddEditActions,
) {

    var isAdvancedOpened = rememberSaveable {
        mutableStateOf(false)
    }

    Column(
        Modifier
            .padding(paddingValues), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MyTextfield(
            value = data.title ?: "",
            onValueChange = { actions.onTitleChanged(it) },
            leadingIcon = null,
            label = stringResource(R.string.title),
            onClearClick = { actions.onTitleChanged(null) })

        MyTextfield(
            value = data.author ?: "",
            onValueChange = { actions.onAuthorChanged(it) },
            leadingIcon = null,
            label = stringResource(id = R.string.author),
            onClearClick = { actions.onAuthorChanged(null) })

        MyTextfield(
            value = data.isbn ?: "",
            onValueChange = { },
            leadingIcon = null,
            label = "ISBN",
            onClearClick = { })

        // Publisher
//        MyTextfield(
//            value = data.publisher ?: "",
//            onValueChange = { actions.onPublisherChanged(it) },
//            leadingIcon = null,
//            label = "Publisher",
//            onClearClick = { actions.onPublisherChanged(null) })

        // PublishedDate
//        MyTextfield(
//            value = data.publishDate ?: "",
//            onValueChange = { actions.onTitleChanged(it) },
//            leadingIcon = null,
//            label = stringResource(R.string.title),
//            onClearClick = { actions.onTitleChanged(null) })

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
                onValueChange = { },
                leadingIcon = null,
                label = stringResource(R.string.subtitle),
                onClearClick = { }
            )

//            MyTextfield(
//                value = data.translator ?: "",
//                onValueChange = { },
//                leadingIcon = null,
//                label = stringResource(R.string.translator),
//                onClearClick = { })

//            MyTextfield(
//                value = data.genre ?: "",
//                onValueChange = { },
//                leadingIcon = null,
//                label = stringResource(R.string.genre),
//                onClearClick = { }
//            )

        }


    }

}
