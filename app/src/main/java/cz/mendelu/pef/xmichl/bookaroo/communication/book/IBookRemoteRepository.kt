package cz.mendelu.pef.xmichl.bookaroo.communication.book

import cz.mendelu.pef.xmichl.bookaroo.architecture.CommunicationResult
import cz.mendelu.pef.xmichl.bookaroo.model.Book

interface IBookRemoteRepository {

    suspend fun fetchBooks(): CommunicationResult<List<Book>>

    suspend fun fetchBook(bookId: String): CommunicationResult<Book>

    suspend fun fetchBooksFromLibrary(libraryId: String): CommunicationResult<List<Book>>

    suspend fun createBook(book: Book): CommunicationResult<Book>

}