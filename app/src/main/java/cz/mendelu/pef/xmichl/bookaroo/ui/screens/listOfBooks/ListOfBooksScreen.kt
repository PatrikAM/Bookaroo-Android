package cz.mendelu.pef.xmichl.bookaroo.ui.screens.listOfBooks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import cz.mendelu.pef.xmichl.bookaroo.R
import cz.mendelu.pef.xmichl.bookaroo.model.Book
import cz.mendelu.pef.xmichl.bookaroo.model.UiState
import cz.mendelu.pef.xmichl.bookaroo.ui.elements.BaseScreen
import cz.mendelu.pef.xmichl.bookaroo.ui.elements.BookarooDialog
import cz.mendelu.pef.xmichl.bookaroo.ui.elements.BookarooSmallCard
import cz.mendelu.pef.xmichl.bookaroo.ui.elements.PlaceholderScreenContent
import cz.mendelu.pef.xmichl.bookaroo.ui.screens.destinations.BarcodeScannerScreenDestination
import cz.mendelu.pef.xmichl.bookaroo.ui.screens.destinations.BookAddEditScreenDestination
import cz.mendelu.pef.xmichl.bookaroo.ui.screens.destinations.BookDetailScreenDestination
import cz.mendelu.pef.xmichl.bookaroo.ui.screens.destinations.ListOfBooksScreenDestination
import cz.mendelu.pef.xmichl.bookaroo.ui.theme.basicMargin
import cz.mendelu.pef.xmichl.bookaroo.ui.theme.getTintColor
import cz.mendelu.pef.xmichl.bookaroo.ui.theme.headLine
import cz.mendelu.pef.xmichl.bookaroo.ui.theme.smallMargin

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
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

    var expanded by remember { mutableStateOf(false) }

    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)

    if (expanded) {
        ModalBottomSheet(
            onDismissRequest = { expanded = false }
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.using_isbn_or_manually),
                    style = headLine()
                )

                Spacer(modifier = Modifier.padding(basicMargin()))

                Button(onClick = {
                    checkCamPermsAndNavigate(
                        cameraPermissionState,
                        onShouldShowRationale = {
                            viewModel.onPermissionError()
                        }
                    ) {
                        navigator.navigate(BarcodeScannerScreenDestination(libraryId = libraryId))
                    }
                }) {
                    Icon(
                        Icons.Default.DocumentScanner,
                        contentDescription = null
                    )
                    Text(
                        text = stringResource(R.string.scan_isbn)
                    )
                }
                Spacer(modifier = Modifier.padding(bottom = smallMargin()))

                OutlinedButton(onClick = {
                    navigator.navigate(BookAddEditScreenDestination(libraryId = libraryId))
                }) {
                    Text(text = stringResource(R.string.crate_manually))
                }

                Spacer(modifier = Modifier.padding(bottom = basicMargin()))

                Text(
                    text = stringResource(R.string.bookaroo_allows_isbn_scanning),
                    modifier = Modifier
                        .padding(40.dp),
                    textAlign = TextAlign.Justify
                )
            }
        }
    }

    if (uiState.value.permissionError) {
        BookarooDialog(
            content = PlaceholderScreenContent
                (
                text = stringResource(R.string.bookaroo_needs_to_use_camera),
                image = R.drawable.ic_camera
            )
        ) {
            viewModel.onPermissionSuccess()
        }
    }

    BaseScreen(
        topBarText = stringResource(R.string.books),
        onBackClick =
        if (libraryId != null) {
            { navigator.popBackStack() }
        } else null,
        drawFullScreenContent = true,
        showLoading = uiState.value.loading,
        placeholderScreenContent = if (uiState.value.errors != null &&
            !uiState.value.loading
        ) {
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
                expanded = true
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
            },
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

@ExperimentalPermissionsApi
//@Composable
private fun checkCamPermsAndNavigate(
    cameraPermissionState: PermissionState,
    onShouldShowRationale: () -> Unit,
    onAllowed: () -> Unit,
) {
    if (cameraPermissionState.status.isGranted) {
        onAllowed()
    } else if (cameraPermissionState.status.shouldShowRationale) {
        onShouldShowRationale()
    } else {
        cameraPermissionState.run { launchPermissionRequest() }
    }
}
