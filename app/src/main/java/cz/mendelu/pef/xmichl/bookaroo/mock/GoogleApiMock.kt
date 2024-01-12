package cz.mendelu.pef.xmichl.bookaroo.mock

import cz.mendelu.pef.xmichl.bookaroo.model.BookShop
import cz.mendelu.pef.xmichl.bookaroo.model.GPlace
import cz.mendelu.pef.xmichl.bookaroo.model.GPlaces

object GoogleApiMock {

    val bookShop1: BookShop = BookShop(
        name = "Luxor",
        place_id = "bookShop1",
        formatted_address = "Brno-Střed, Nějaká Ulice 1",
        international_phone_number = "+420 990 123 459",
        formatted_phone_number = "+420 990 123 459"
    )

    val bookShop2: BookShop = BookShop(
        name = "Dobrovsky",
        place_id = "bookShop2",
        formatted_address = "Brno-Venkoc, Nějaká Ulice 2",
        international_phone_number = "+420 930 622 459",
        formatted_phone_number = "+420 930 622 459"
    )

    val bookShop3: BookShop = BookShop(
        name = "Arrakis",
        place_id = "bookShop3",
        formatted_address = "Brno, Nějaká Ulice 4",
        international_phone_number = "+420 730 662 355",
        formatted_phone_number = "+420 730 662 355"
    )

    val allBookShops = listOf(
        bookShop1,
        bookShop2,
        bookShop3
    )

    val allGPlaces = listOf(
        GPlace(bookShop1),
        GPlace(bookShop2),
        GPlace(bookShop3),
    )

    val gPlaces = GPlaces(
        next_page_token = null,
        results = allBookShops
    )
}