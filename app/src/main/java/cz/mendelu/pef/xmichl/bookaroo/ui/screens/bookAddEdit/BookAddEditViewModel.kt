package cz.mendelu.pef.xmichl.bookaroo.ui.screens.bookAddEdit

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import cz.mendelu.pef.xmichl.bookaroo.R
import cz.mendelu.pef.xmichl.bookaroo.architecture.BaseViewModel
import cz.mendelu.pef.xmichl.bookaroo.architecture.CommunicationResult
import cz.mendelu.pef.xmichl.bookaroo.communication.book.IBookRemoteRepository
import cz.mendelu.pef.xmichl.bookaroo.model.Book
import cz.mendelu.pef.xmichl.bookaroo.model.UiState
import cz.mendelu.pef.xmichl.bookaroo.ui.screens.bookDetail.BookErrors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookAddEditViewModel
@Inject constructor(
    private val repository: IBookRemoteRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel(), BookAddEditActions {


    private val bookId: String? =
            savedStateHandle.get<String>("bookId")

    val uiState: MutableState<UiState<Book, BookErrors>> =
        mutableStateOf(UiState())

    init {
        uiState.value = UiState(
            loading = false,
            data = Book(),
            errors = null
        )
    }


    override fun saveBook() {
        launch {
            when (val result =
                repository.createBook(uiState.value.data!!)
            ) {
                is CommunicationResult.CommunicationError -> {
                    uiState.value = UiState(
                        loading = false,
                        data = null,
                        errors = BookErrors(R.string.no_internet_connection)
                    )
                }

                is CommunicationResult.Error -> {
                    uiState.value = UiState(
                        loading = false,
                        data = null,
                        errors = BookErrors(R.string.failed_to_fetch_this_book)
                    )
                }

                is CommunicationResult.Exception -> {
                    uiState.value = UiState(
                        loading = false,
                        data = null,
                        errors = BookErrors(R.string.unknown_error)
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

    override fun onTitleChanged(title: String?) {
        TODO("Not yet implemented")
    }

    override fun onSubTitleChanged(subTitle: String?) {
        TODO("Not yet implemented")
    }

    override fun onAuthorChanged(title: String?) {
        TODO("Not yet implemented")
    }

    override fun onLibraryChanged(library: String?) {
        TODO("Not yet implemented")
    }

}