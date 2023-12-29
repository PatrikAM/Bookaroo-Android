package cz.mendelu.pef.xmichl.bookaroo.ui.screens.listOfBooks

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import cz.mendelu.pef.xmichl.bookaroo.R
import cz.mendelu.pef.xmichl.bookaroo.architecture.BaseViewModel
import cz.mendelu.pef.xmichl.bookaroo.architecture.CommunicationResult
import cz.mendelu.pef.xmichl.bookaroo.communication.book.IBookRemoteRepository
import cz.mendelu.pef.xmichl.bookaroo.model.Book
import cz.mendelu.pef.xmichl.bookaroo.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListOfBooksViewModel
@Inject constructor(
    private val repository: IBookRemoteRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel(), IListOfBooksActions {


    private val libraryId: String? =
        if (savedStateHandle.contains("libraryId")) {
            savedStateHandle.get<String>("libraryId")
        } else null

    val uiState: MutableState<UiState<List<Book>, ListOfBooksErrors>> =
        mutableStateOf(UiState())

    init {
        fetchBooks()
    }

    fun refreshBooks() {
        uiState.value = UiState(
            loading = true,
            data = null,
            errors = null
        )
        fetchBooks()
    }


    override fun fetchBooks() {
        launch {
            when (val result =
                if (libraryId == null) {
                    repository.fetchBooks()
                } else {
                    repository.fetchBooksFromLibrary(libraryId)
                }
            ) {
                is CommunicationResult.CommunicationError -> {
                    uiState.value = UiState(
                        loading = false,
                        data = null,
                        errors = ListOfBooksErrors(R.string.no_internet_connection),
                        image = R.drawable.ic_connection
                    )
                }

                is CommunicationResult.Error -> {
                    uiState.value = UiState(
                        loading = false,
                        data = null,
                        errors = ListOfBooksErrors(R.string.failed_to_log_in)
                    )
                }

                is CommunicationResult.Exception -> {
                    uiState.value = UiState(
                        loading = false,
                        data = null,
                        errors = ListOfBooksErrors(R.string.unknown_error)
                    )
                }

                is CommunicationResult.Success -> {
                    uiState.value = UiState(
                        loading = false,
                        data = result.data,
                        errors = null
                    )
                }
            }
        }
    }

    override fun onPermissionError() {
        uiState.value.permissionError = true
        uiState.value = UiState(
            loading = uiState.value.loading,
            data = uiState.value.data,
            errors = uiState.value.errors,
            permissionError = uiState.value.permissionError
        )
    }

    override fun onPermissionSuccess() {
        uiState.value.permissionError = false
        uiState.value = UiState(
            loading = uiState.value.loading,
            data = uiState.value.data,
            errors = uiState.value.errors,
            permissionError = uiState.value.permissionError
        )
    }


}