package cz.mendelu.pef.xmichl.bookaroo.communication.book

import cz.mendelu.pef.xmichl.bookaroo.architecture.CommunicationResult
import cz.mendelu.pef.xmichl.bookaroo.architecture.IBaseRemoteRepository
import cz.mendelu.pef.xmichl.bookaroo.datastore.DataStoreRepositoryImpl
import cz.mendelu.pef.xmichl.bookaroo.model.Book
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BookRemoteRepositoryImpl @Inject constructor(
    private val booksApi: BooksApi,
    private val dataStoreRepository: DataStoreRepositoryImpl
) : IBaseRemoteRepository, IBookRemoteRepository {

    override suspend fun fetchBooks()
            : CommunicationResult<List<Book>> {
//        val response = withContext(Dispatchers.IO) {
//            booksApi.fetchBooks(dataStoreRepository.getUserToken()!!)
//        }
//        return processResponse(response)
        return processResponse {
                booksApi.fetchBooks(
                    dataStoreRepository.getUserToken()!!
                )
        }
    }

    override suspend fun fetchBook(bookId: String)
            : CommunicationResult<Book> {
//        val response = withContext(Dispatchers.IO) {
//            booksApi.fetchBook(
//                bookId = bookId,
//                token = dataStoreRepository.getUserToken()!!
//            )
//        }
//        return processResponse(response)
        return processResponse {
            booksApi.fetchBook(
                bookId = bookId,
                token = dataStoreRepository.getUserToken()!!
            )
        }
    }

    override suspend fun fetchBooksFromLibrary(libraryId: String)
            : CommunicationResult<List<Book>> {
//        val response = withContext(Dispatchers.IO) {
//            booksApi.fetchBooksFromLibrary(libraryId, dataStoreRepository.getUserToken()!!)
//        }
//        return processResponse(response)
        return processResponse {
            booksApi.fetchBooksFromLibrary(
                libraryId,
                dataStoreRepository.getUserToken()!!
            )
        }
    }

    override suspend fun createBook(book: Book)
            : CommunicationResult<Book> {
//        val response = withContext(Dispatchers.IO) {
//            booksApi.createBook(
//                book,
//                dataStoreRepository.getUserToken()!!
//            )
//        }
//        return processResponse(response)
        return processResponse {
            booksApi.createBook(
                book,
                dataStoreRepository.getUserToken()!!
            )
        }
    }

    suspend fun createaltBook(book: Book)
            : CommunicationResult<Book> {

        return processResponse {
            booksApi.createBook(
                book,
                dataStoreRepository.getUserToken()!!
            )
        }
//        val response = withContext(Dispatchers.IO) {
//            booksApi.createBook(
//                book,
//                dataStoreRepository.getUserToken()!!
//            )
//        }
//        return processResponse(response)
    }

    override suspend fun updateBook(book: Book)
            : CommunicationResult<Book> {
        TODO("Not yet implemented")
    }
}