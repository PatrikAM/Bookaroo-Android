package cz.mendelu.pef.xmichl.bookaroo.ui.screens.bookAddEdit

interface BookAddEditActions {

    fun onTitleChanged(title: String?)

    fun onSubTitleChanged(subTitle: String?)

    fun onAuthorChanged(title: String?)

    fun onLibraryChanged(library: String?)

    fun onNewLibrary(library: String)
    fun onPublisherChanged(publisher: String?)

    fun onPublishedChanged(published: String?)

    fun saveBook()
    fun onPagesChanged(pages: String?)
    fun onCoverChanged(cover: String?)
    fun onISBNChanged(isbn: String?)

    fun onDialogDismiss()

}