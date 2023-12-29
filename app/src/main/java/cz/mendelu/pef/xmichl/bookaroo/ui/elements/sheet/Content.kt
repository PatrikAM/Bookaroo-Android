package cz.mendelu.pef.xmichl.bookaroo.ui.elements.sheet

sealed class Content {
    data class Center(val valueText: String): Content()
    data class Left(val valueText: String): Content()
}
