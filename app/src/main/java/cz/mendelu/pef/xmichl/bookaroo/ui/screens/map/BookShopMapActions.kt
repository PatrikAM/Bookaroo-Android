package cz.mendelu.pef.xmichl.bookaroo.ui.screens.map

import cz.mendelu.pef.xmichl.bookaroo.model.BookShop

interface BookShopMapActions {

    fun onClusterItemClick(item: BookShop)

    fun onDetailDismiss()

}
