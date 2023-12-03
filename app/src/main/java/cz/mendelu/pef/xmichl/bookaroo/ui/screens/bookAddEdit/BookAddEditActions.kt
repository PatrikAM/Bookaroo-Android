package cz.mendelu.pef.xmichl.bookaroo.ui.screens.bookAddEdit

interface BookAddEditActions {

    fun onTitleChanged(title: String?)

    fun onSubTitleChanged(subTitle: String?)

    fun onAuthorChanged(title: String?)

    fun onLibraryChanged(library: String?)

    fun saveBook()

}