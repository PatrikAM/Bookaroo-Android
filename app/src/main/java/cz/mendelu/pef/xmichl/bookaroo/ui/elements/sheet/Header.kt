package cz.mendelu.pef.xmichl.bookaroo.ui.elements.sheet

sealed class Header {
    data class HeaderPlain(val titleText: String) : Header()
    data class HeaderImage(val titleText: String, val imageResourceId: Int?) : Header()
}