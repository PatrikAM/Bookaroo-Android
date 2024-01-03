package cz.mendelu.pef.xmichl.bookaroo.ui.screens.bookDetail

import android.content.Context

interface BookActions {
    fun fetchBook()
    fun deleteBook()
    fun insertToClipboard(label: String, text: String, context: Context)
}