package cz.mendelu.pef.xmichl.bookaroo.fakeRepostiory

import cz.mendelu.pef.xmichl.bookaroo.architecture.CommunicationResult
import cz.mendelu.pef.xmichl.bookaroo.communication.book.IBookRemoteRepository
import cz.mendelu.pef.xmichl.bookaroo.mock.BokarooBooksServerMock
import cz.mendelu.pef.xmichl.bookaroo.model.Book
import javax.inject.Inject

class BooksFakeRepository @Inject constructor(): IBookRemoteRepository {

    override suspend fun fetchBooks(): CommunicationResult<List<Book>> {
        return CommunicationResult.Success(
            BokarooBooksServerMock.all
        )
    }

    override suspend fun fetchBook(bookId: String): CommunicationResult<Book> {
        val books = BokarooBooksServerMock.all.filter { it.id == bookId }
        return if (books.isNotEmpty()) {
            CommunicationResult.Success(books[0])
        } else {
            CommunicationResult.CommunicationError()
        }
    }

    override suspend fun fetchBooksFromLibrary(libraryId: String): CommunicationResult<List<Book>> {
        val books = BokarooBooksServerMock.all.filter { it.library == libraryId }
        return if (books.isNotEmpty()) {
            CommunicationResult.Success(books)
        } else {
            CommunicationResult.CommunicationError()
        }
    }

    override suspend fun createBook(book: Book): CommunicationResult<Book> {
        val book1 = book.copy()
        book1.id = "book1"
        return CommunicationResult.Success(book1)
    }

    override suspend fun updateBook(book: Book): CommunicationResult<Book> {
        return CommunicationResult
            .Success(
                book
            )
    }

    override suspend fun deleteBook(bookId: String): CommunicationResult<Book> {
        return CommunicationResult
            .Success(
                BokarooBooksServerMock
                    .all
                    .first { book: Book -> book.id == bookId }
            )
    }

}