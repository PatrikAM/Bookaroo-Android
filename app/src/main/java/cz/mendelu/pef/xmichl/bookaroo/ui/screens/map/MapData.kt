package cz.mendelu.pef.xmichl.bookaroo.ui.screens.map

import cz.mendelu.pef.xmichl.bookaroo.model.BookShop

data class MapData (
    var places: List<BookShop> = mutableListOf()
)
