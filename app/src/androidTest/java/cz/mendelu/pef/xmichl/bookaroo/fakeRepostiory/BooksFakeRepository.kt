package cz.mendelu.pef.xmichl.bookaroo.fakeRepostiory

import cz.mendelu.pef.xmichl.bookaroo.architecture.CommunicationResult
import cz.mendelu.pef.xmichl.bookaroo.communication.book.IBookRemoteRepository
import cz.mendelu.pef.xmichl.bookaroo.mock.BokarooBooksServerMock
import cz.mendelu.pef.xmichl.bookaroo.model.Book

class BooksFakeRepository: IBookRemoteRepository {

    override suspend fun fetchBooks(): CommunicationResult<List<Book>> {
        return CommunicationResult.Success(
            BokarooBooksServerMock.all
        )
    }

    override suspend fun fetchBook(bookId: String): CommunicationResult<Book> {
        val books = BokarooBooksServerMock.all.filter { it.id == bookId }
        if (books.isNotEmpty()) {
            return CommunicationResult.Success(books[0])
        } else {
            return CommunicationResult.CommunicationError()
        }
    }

    override suspend fun fetchBooksFromLibrary(libraryId: String): CommunicationResult<List<Book>> {
        val books = BokarooBooksServerMock.all.filter { it.library == libraryId }
        if (books.isNotEmpty()) {
            return CommunicationResult.Success(books)
        } else {
            return CommunicationResult.CommunicationError()
        }
    }

    override suspend fun createBook(book: Book): CommunicationResult<Book> {
        TODO("Not yet implemented")
    }

    override suspend fun updateBook(book: Book): CommunicationResult<Book> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteBook(bookId: String): CommunicationResult<Book> {
        TODO("Not yet implemented")
    }

}