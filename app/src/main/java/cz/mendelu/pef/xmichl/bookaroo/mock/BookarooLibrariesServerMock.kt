package cz.mendelu.pef.xmichl.bookaroo.mock

import cz.mendelu.pef.xmichl.bookaroo.model.Book
import cz.mendelu.pef.xmichl.bookaroo.model.Library

object BokarooLibrariesServerMock {

    enum class Library(s: String) {
        lib1("Lib1"),
        lib2("Lib2"),
        lib3("Lib3")
    }

    enum class Name(s: String) {
        lib1("Lib 1"),
        lib2("Lib 2"),
        lib3("Lib 3")
    }

    val lib1 = Library(
        id = Library.lib1.name,
        name = Name.lib1.name,
        ownerId = "user1"
    )

    val lib2 = Library(
        id = Library.lib2.name,
        name = Name.lib2.name,
        ownerId = "user1"
    )

    val lib3 = Library(
        id = Library.lib3.name,
        name = Name.lib3.name,
        ownerId = "user2"
    )

    val all = listOf(
        lib1,
        lib2,
        lib3
    )

}
