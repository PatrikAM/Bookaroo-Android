package cz.mendelu.pef.xmichl.bookaroo.ui.screens.bookAddEdit

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import cz.mendelu.pef.xmichl.bookaroo.R
import cz.mendelu.pef.xmichl.bookaroo.architecture.BaseViewModel
import cz.mendelu.pef.xmichl.bookaroo.architecture.CommunicationResult
import cz.mendelu.pef.xmichl.bookaroo.communication.book.IBookRemoteRepository
import cz.mendelu.pef.xmichl.bookaroo.communication.gbooks.IGBooksRemoteRepository
import cz.mendelu.pef.xmichl.bookaroo.communication.library.ILibraryRemoteRepository
import cz.mendelu.pef.xmichl.bookaroo.model.Book
import cz.mendelu.pef.xmichl.bookaroo.model.Library
import cz.mendelu.pef.xmichl.bookaroo.model.UiState
import cz.mendelu.pef.xmichl.bookaroo.ui.screens.bookDetail.BookErrors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookAddEditViewModel
@Inject constructor(
    private val repository: IBookRemoteRepository,
    private val libRepo: ILibraryRemoteRepository,
    private val gBookRepo: IGBooksRemoteRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel(), BookAddEditActions {


    private val bookId: String? =
            savedStateHandle.get<String>("bookId")

    private val libraryId: String? =
        savedStateHandle.get<String>("libraryId")

    private val isbn: String? =
        savedStateHandle.get<String>("isbn")

    var responseError: Int? = null
    var showDialog: Boolean = false

    var bookErrors: BookValueErrors = BookValueErrors()
    var image: Int? = null

    var uiState: MutableState<UiState<Book, BookErrors>> =
        mutableStateOf(UiState())

    var uiLibState: MutableState<UiState<List<Library>, LibraryErrors>> =
        mutableStateOf(UiState())

    init {

        uiState.value = UiState(
            loading = false,
            data = Book(isbn=isbn),
            errors = null
        )

        if (bookId != null) {
            fetchBook(bookId)
        } else if (isbn != null) {
            fetchBookByIsbn(isbn)

        } else {
            uiState.value = UiState(
                loading = false,
                data = Book(isbn=isbn),
                errors = null
            )
        }
        onLibraryChanged(libraryId)
        onISBNChanged(isbn)
        fetchLibraries()
    }

    private fun fetchBookByIsbn(isbn: String) {
        launch {
            val result =
                gBookRepo.getBookByIsbn(isbn = isbn)
            when (
                result
            ) {

                is CommunicationResult.CommunicationError -> {

                    uiState.value = UiState(
                        loading = false,
                        data = null,
                        image = R.drawable.ic_connection,
                        errors = BookErrors(R.string.no_internet_connection)
                    )
                }

                is CommunicationResult.Error -> {
                    uiState.value = UiState(
                        loading = false,
                        data = null,
                        image = R.drawable.ic_bookshelves,
                        errors = BookErrors(R.string.failed_to_fetch_this_book)
                    )
                }

                is CommunicationResult.Exception -> {
                    uiState.value = UiState(
                        loading = false,
                        data = null,
                        image = R.drawable.ic_book_lover,
                        errors = BookErrors(R.string.unknown_error)
                    )
                }

                is CommunicationResult.Success -> {
                    Log.d("error", result.data.toString())
                    if (result.data.items?.get(0)?.convert() == null) {
                        uiState.value = UiState(
                            loading = false,
                            data = null,
                            image = R.drawable.ic_bookshelves,
                            errors = BookErrors(R.string.failed_to_fetch_this_book)
                        )
                    } else {
                        uiState.value = UiState(
                            loading = false,
                            data = result.data.items[0].convert(),
                            image = null,
                            errors = null
                        )
                    }
                }
            }
            responseError = uiState.value.errors?.communicationError
            image = uiState.value.image
            showDialog = responseError != null
            onLibraryChanged(libraryId)
            onISBNChanged(isbn)
        }
    }


    override fun saveBook() {
        var isError: Boolean = false
        if (uiState.value.data?.title == null) {
            bookErrors.titleError = R.string.required_field
            isError = true
        }
        if (uiState.value.data?.author == null) {
            bookErrors.authorError = R.string.required_field
            isError = true
        }
        if (uiState.value.data?.library == null) {
            bookErrors.libraryError = R.string.required_field
            isError = true
        }
        if (uiState.value.data?.isbn == null) {
            bookErrors.isbnError = R.string.required_field
            isError = true
        }
        if (isError) {
            // changeTheState
            image = R.drawable.ic_book_lover
            responseError = R.string.please_fill_the_form_correctly
            showDialog = true
            bookChanged()
            return
        }
        launch {
            when (val result =
                if (bookId == null) {
                    repository.createBook(uiState.value.data!!)
                } else {
                    repository.updateBook(uiState.value.data!!)
                }
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
            responseError = uiState.value.errors?.communicationError
            image = uiState.value.image
            showDialog = responseError != null
            bookChanged()
        }
    }

    override fun onPagesChanged(pages: String?) {
        if (pages?.toIntOrNull() != null) {
            uiState.value.data?.pages = pages.toInt()
            bookChanged()
        }

        if (pages == null) {
            uiState.value.data?.pages = null
            bookChanged()
        }
    }

    override fun onCoverChanged(cover: String?) {
        uiState.value.data?.cover = cover
        bookChanged()
    }

    override fun onISBNChanged(isbn: String?) {
        uiState.value.data?.isbn = isbn
        bookChanged()
    }

    override fun onTitleChanged(title: String?) {
        uiState.value.data?.title = title
        bookChanged()
    }

    override fun onSubTitleChanged(subTitle: String?) {
        uiState.value.data?.subtitle = subTitle
        bookChanged()
    }

    override fun onAuthorChanged(author: String?) {
        uiState.value.data?.author = author
        bookChanged()
    }

    override fun onLibraryChanged(library: String?) {
        uiState.value.data?.library = library
        bookChanged()
    }

    fun fetchLibraries() {
        launch {
            when (val result =
                libRepo.fetchLibraries()
            ) {
                is CommunicationResult.CommunicationError -> {
                    uiLibState.value = UiState(
                        loading = true,
                        data = null,
                        image = R.drawable.ic_connection,
                        errors = LibraryErrors(R.string.no_internet_connection)
                    )
                }

                is CommunicationResult.Error -> {
                    uiLibState.value = UiState(
                        loading = true,
                        data = null,
                        image = R.drawable.ic_bookshelves,
                        errors = LibraryErrors(R.string.failed_to_fetch_this_book)
                    )
                }

                is CommunicationResult.Exception -> {
                    uiLibState.value = UiState(
                        loading = true,
                        data = null,
                        image = R.drawable.ic_book_lover,
                        errors = LibraryErrors(R.string.unknown_error)
                    )
                }

                is CommunicationResult.Success -> {
                    uiLibState.value = UiState(
                        loading = true,
                        data = result.data,
                        errors = null
                    )
                }
            }
            responseError = uiLibState.value.errors?.communicationError
            image = uiLibState.value.image
            showDialog = responseError != null
            bookChanged()
        }
    }

    override fun onNewLibrary(library: String) {
        launch {
            when (val result =
                libRepo.createLibrary(library = library)
            ) {
                is CommunicationResult.CommunicationError -> {
                    uiLibState.value = UiState(
                        loading = false,
                        data = null,
                        errors = LibraryErrors(R.string.no_internet_connection)
                    )
                }

                is CommunicationResult.Error -> {
                    uiLibState.value = UiState(
                        loading = false,
                        data = null,
                        errors = LibraryErrors(R.string.failed_to_fetch_this_book)
                    )
                }

                is CommunicationResult.Exception -> {
                    uiLibState.value = UiState(
                        loading = false,
                        data = null,
                        errors = LibraryErrors(R.string.unknown_error)
                    )
                }

                is CommunicationResult.Success -> {
                    uiLibState.value = UiState(
                        loading = false,
                        data = uiLibState.value.data?.plus(result.data),
                        errors = null
                    )
                    fetchLibraries()
                }

            }
            responseError = uiLibState.value.errors?.communicationError
            image = uiLibState.value.image
            showDialog = responseError != null
            bookChanged()
        }
    }

    override fun onPublisherChanged(publisher: String?) {
        uiState.value.data?.publisher = publisher
        bookChanged()
    }

    override fun onPublishedChanged(published: String?) {
        uiState.value.data?.published = published
        bookChanged()
    }

    fun bookChanged() {
        uiState.value = UiState(
            loading = uiState.value.loading,
            data = uiState.value.data,
            errors = uiState.value.errors,
            actionDone = uiState.value.actionDone
        )
    }

    override fun onDialogDismiss() {
        showDialog = false
        bookChanged()
    }

    fun fetchBook(id: String) {
        launch {
            when (val result =
                repository.fetchBook(id)
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
                        errors = null
                    )
                }
            }
            responseError = uiState.value.errors?.communicationError
            image = uiState.value.image
            showDialog = responseError != null
            bookChanged()
        }
    }

}