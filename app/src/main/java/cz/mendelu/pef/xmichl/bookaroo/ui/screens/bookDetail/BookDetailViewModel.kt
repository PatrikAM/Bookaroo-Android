package cz.mendelu.pef.xmichl.bookaroo.ui.screens.bookDetail

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import cz.mendelu.pef.xmichl.bookaroo.R
import cz.mendelu.pef.xmichl.bookaroo.architecture.BaseViewModel
import cz.mendelu.pef.xmichl.bookaroo.architecture.CommunicationResult
import cz.mendelu.pef.xmichl.bookaroo.communication.book.IBookRemoteRepository
import cz.mendelu.pef.xmichl.bookaroo.communication.library.ILibraryRemoteRepository
import cz.mendelu.pef.xmichl.bookaroo.model.Book
import cz.mendelu.pef.xmichl.bookaroo.model.Library
import cz.mendelu.pef.xmichl.bookaroo.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel
@Inject constructor(
    private val repository: IBookRemoteRepository,
    private val libRepository: ILibraryRemoteRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel(), BookActions {


    private val bookId: String =
            savedStateHandle.get<String>("bookId")!!

    val uiState: MutableState<UiState<Book, BookErrors>> =
        mutableStateOf(UiState())

    var library: Library? = null

    init {
        fetchBook()
    }

    override fun insertToClipboard(label: String, text: String, context: Context) {
        val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        val clip = ClipData.newPlainText(label, text)
        clipboardManager.setPrimaryClip(clip)
    }

    fun refreshBook() {
        uiState.value = UiState(
            loading = true,
            data = null,
            errors = null
        )
        fetchBook()
    }


    override fun fetchBook() {
        launch {
            when (val result =
                repository.fetchBook(bookId)
            ) {
                is CommunicationResult.CommunicationError -> {
                    uiState.value = UiState(
                        loading = false,
                        data = null,
                        errors = BookErrors(R.string.no_internet_connection),
                        image = R.drawable.ic_connection
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
//                    uiState.value = UiState(
//                        loading = false,
//                        data = result.data,
//                        errors = null
//                    )
                    uiState.value.data = result.data
                    fetchLibrary()
                }
            }
        }
    }

    private fun fetchLibrary() {
        launch {
            when (val result =
                libRepository.fetchLibrary(uiState.value.data!!.library!!)
            ) {
                is CommunicationResult.CommunicationError -> {
                    uiState.value.loading = false
                    bookChanged()
                }

                is CommunicationResult.Error -> {
                    uiState.value.loading = false
                    bookChanged()
                }

                is CommunicationResult.Exception -> {
                    uiState.value.loading = false
                    bookChanged()
                }

                is CommunicationResult.Success -> {
                    library = result.data
                    uiState.value.loading = false
                    bookChanged()
                }
            }
        }
    }

    override fun deleteBook() {
        launch {
            when (val result =
                repository.deleteBook(bookId)
            ) {
                is CommunicationResult.CommunicationError -> {
                    uiState.value = UiState(
                        loading = false,
                        data = null,
                        errors = BookErrors(R.string.no_internet_connection),
                        image = R.drawable.ic_connection
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
                        errors = null,
                        actionDone = true
                    )
                }
            }
//            bookChanged()
        }
    }

    private fun bookChanged() {
        uiState.value = UiState(
            loading = uiState.value.loading,
            data = uiState.value.data,
            errors = uiState.value.errors,
        )
    }

}