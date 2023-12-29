package cz.mendelu.pef.xmichl.bookaroo.ui.screens.listOfBooks

interface IListOfBooksActions {
    fun fetchBooks()

    fun onPermissionError()

    fun onPermissionSuccess()
}