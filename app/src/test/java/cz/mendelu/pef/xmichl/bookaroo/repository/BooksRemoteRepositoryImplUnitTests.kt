package cz.mendelu.pef.xmichl.bookaroo.repository

import cz.mendelu.pef.xmichl.bookaroo.architecture.CommunicationResult
import cz.mendelu.pef.xmichl.bookaroo.communication.book.BookRemoteRepositoryImpl
import cz.mendelu.pef.xmichl.bookaroo.communication.book.BooksApi
import cz.mendelu.pef.xmichl.bookaroo.datastore.DataStoreRepositoryImpl
import cz.mendelu.pef.xmichl.bookaroo.mock.BokarooBooksServerMock
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Test
import retrofit2.Response
import java.io.IOException
import java.net.UnknownHostException

class BooksRemoteRepositoryImplUnitTests {
    private val api = mockk<BooksApi>()
    private val dataStore = mockk<DataStoreRepositoryImpl>()

    private val repository = BookRemoteRepositoryImpl(api, dataStore)

    private val books = BokarooBooksServerMock.all
    private val book = BokarooBooksServerMock.Book1

    @Test
    fun test_fetch_books_success() = runBlocking {
        coEvery {
            api.fetchBooks("user-1")
        } returns Response.success(books)

        coEvery {
            dataStore.getUserToken()
        } returns "user-1"

        val result = repository.fetchBooks()

        Assert.assertTrue(result is CommunicationResult.Success)

        val data = (result as CommunicationResult.Success).data

        Assert.assertEquals(data.size, books.size)
        books.forEach {
            Assert.assertTrue(data.contains(it))
        }
    }

    @Test
    fun test_fetch_books_error() = runBlocking {

        val errorCode = 404
        val errorResponseBody =
            ResponseBody.create("application/json".toMediaTypeOrNull(), "Error body content")

        coEvery {
            api.fetchBooks("user-1")
        } returns Response.error(errorCode, errorResponseBody)

        coEvery {
            dataStore.getUserToken()
        } returns "user-1"

        val result = repository.fetchBooks()

        Assert.assertTrue(result is CommunicationResult.Error)

    }

    @Test
    fun test_fetch_books_exception() = runBlocking {
        coEvery { api.fetchBooks("user-1") } throws Exception()
        coEvery {
            dataStore.getUserToken()
        } returns "user-1"

        val result = repository.fetchBooks()

        Assert.assertTrue(result is CommunicationResult.Exception)
    }

    @Test
    fun test_fetch_books_communicationError() = runBlocking {
        coEvery { api.fetchBooks("user-1") } throws UnknownHostException()
        coEvery {
            dataStore.getUserToken()
        } returns "user-1"

        val result = repository.fetchBooks()

        Assert.assertTrue(result is CommunicationResult.CommunicationError)
    }

    @Test
    fun test_fetch_books_IOException() = runBlocking {
        coEvery { api.fetchBooks("user-1") } throws IOException()
        coEvery {
            dataStore.getUserToken()
        } returns "user-1"

        val result = repository.fetchBooks()

        Assert.assertTrue(result is CommunicationResult.Exception)
    }


    @Test
    fun test_fetch_book_success() = runBlocking {
        coEvery {
            api.fetchBook(book.id!!, "user-1")
        } returns Response.success(book)

        coEvery {
            dataStore.getUserToken()
        } returns "user-1"

        val result = repository.fetchBook(book.id!!)

        Assert.assertTrue(result is CommunicationResult.Success)

        val data = (result as CommunicationResult.Success).data

        Assert.assertEquals(data, book)
    }

    @Test
    fun test_fetch_book_error() = runBlocking {

        val errorCode = 404
        val errorResponseBody =
            ResponseBody.create("application/json".toMediaTypeOrNull(), "Error body content")

        coEvery {
            api.fetchBook(book.id!!, "user-1")
        } returns Response.error(errorCode, errorResponseBody)

        coEvery {
            dataStore.getUserToken()
        } returns "user-1"

        val result = repository.fetchBook(book.id!!)

        Assert.assertTrue(result is CommunicationResult.Error)

    }

    @Test
    fun test_fetch_book_exception() = runBlocking {
        coEvery { api.fetchBook(book.id!!,"user-1") } throws Exception()
        coEvery {
            dataStore.getUserToken()
        } returns "user-1"

        val result = repository.fetchBook(book.id!!)

        Assert.assertTrue(result is CommunicationResult.Exception)
    }

    @Test
    fun test_fetch_book_communicationError() = runBlocking {
        coEvery { api.fetchBook(book.id!!, "user-1") } throws UnknownHostException()
        coEvery {
            dataStore.getUserToken()
        } returns "user-1"

        val result = repository.fetchBook(book.id!!)

        Assert.assertTrue(result is CommunicationResult.CommunicationError)
    }

    @Test
    fun test_fetch_book_IOException() = runBlocking {
        coEvery { api.fetchBook(book.id!!, "user-1") } throws IOException()
        coEvery {
            dataStore.getUserToken()
        } returns "user-1"

        val result = repository.fetchBook(book.id!!)

        Assert.assertTrue(result is CommunicationResult.Exception)
    }

}