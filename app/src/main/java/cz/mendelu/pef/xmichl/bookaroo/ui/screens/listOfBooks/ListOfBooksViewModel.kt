package cz.mendelu.pef.xmichl.bookaroo.ui.screens.listOfBooks

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cz.mendelu.pef.xmichl.bookaroo.architecture.BaseViewModel
import cz.mendelu.pef.xmichl.bookaroo.communication.library.ILibraryRemoteRepository
import cz.mendelu.pef.xmichl.bookaroo.model.Book
import cz.mendelu.pef.xmichl.bookaroo.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListOfBooksViewModel
@Inject constructor(
    private val repository: ILibraryRemoteRepository,
) : BaseViewModel(), IListOfBooksActions {

    val uiState: MutableState<UiState<List<Book>, ListOfBooksErrors>> =
        mutableStateOf(UiState())

    init {
        fetchBooks()
    }

    fun refreshLibraries() {
        uiState.value = UiState(
            loading = true,
            data = null,
            errors = null
        )
        fetchBooks()
    }


    override fun fetchBooks() {
        launch {
//            when (val result =
//                repository.fetchBooks() ) {
//                is CommunicationResult.CommunicationError -> {
//                    uiState.value = UiState(
//                        loading = false,
//                        data = null,
//                        errors = ListOfBooksErrors(R.string.no_internet_connection)
//                    )
//                }
//
//                is CommunicationResult.Error -> {
//                    uiState.value = UiState(
//                        loading = false,
//                        data = null,
//                        errors = ListOfBooksErrors(R.string.failed_to_log_in)
//                    )
//                }
//
//                is CommunicationResult.Exception -> {
//                    uiState.value = UiState(
//                        loading = false,
//                        data = null,
//                        errors = ListOfBooksErrors(R.string.unknown_error)
//                    )
//                }
//
//                is CommunicationResult.Success -> {
//                    uiState.value = UiState(
//                        loading = true,
//                        data = result.data,
//                        errors = null
//                    )
//                }
//            }
        }
    }

}