package cz.mendelu.pef.xmichl.bookaroo.mock

import cz.mendelu.pef.xmichl.bookaroo.model.Book

object BokarooBooksServerMock {

    enum class Library(s: String) {
        lib1("Lib1"),
        lib2("Lib2"),
        lib3("Lib3")
    }

    enum class Author(s: String) {
        pepa("Pepa Jedna"),
        franta("Franta Dva"),
        honza("Honza Lucemburk")
    }

    val Book1 = Book(
        id = "1",
        library = Library.lib1.name,
        author = Author.franta.name,
        title = "Ahoj Svete",
        isbn = "isbn"
    )

    val Book2 = Book(
        id = "2",
        library = Library.lib1.name,
        author = Author.pepa.name,
        title = "Krasne Leto",
        isbn = "isbn0"
    )

    val Book3 = Book(
        id = "2",
        library = Library.lib2.name,
        author = Author.pepa.name,
        title = "Divne stromy",
        isbn = "isbn1"
    )

    val Book4 = Book(
        id = "2",
        library = Library.lib2.name,
        author = Author.honza.name,
        title = "Kniha o knize",
        isbn = "isbn2"
    )

    val all = listOf(
        Book1,
        Book2,
        Book3,
        Book4
    )

}
