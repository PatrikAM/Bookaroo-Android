package cz.mendelu.pef.xmichl.bookaroo.repository

import cz.mendelu.pef.xmichl.bookaroo.architecture.CommunicationResult
import cz.mendelu.pef.xmichl.bookaroo.communication.gbooks.GBooksApi
import cz.mendelu.pef.xmichl.bookaroo.communication.gbooks.GBooksRemoteRepositoryImpl
import cz.mendelu.pef.xmichl.bookaroo.datastore.DataStoreRepositoryImpl
import cz.mendelu.pef.xmichl.bookaroo.model.GBook
import cz.mendelu.pef.xmichl.bookaroo.model.GBooks
import cz.mendelu.pef.xmichl.bookaroo.model.VolumeInfo
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

class GBooksRemoteRepositoryImplUnitTests {

    private val api = mockk<GBooksApi>()
    private val dataStore = mockk<DataStoreRepositoryImpl>()

    private val repository = GBooksRemoteRepositoryImpl(api)

    private val isbn = "978-80-7033-172-9"

    private val gBook = GBook(
        id = "gbook",
        VolumeInfo(
            title = "title",
            authors = null,
            "Grada",
            "1990",
            "description",
            150,
            null,
            "cs",
            null,
            null,
        )
    )

    @Test
    fun test_fetch_book_success() = runBlocking {
        val list = ArrayList<GBook>()
        list.add(gBook)
        coEvery {
            api.getBookByIsbn(isbn, "")
        } returns Response.success(GBooks(1, list))

        val result = repository.getBookByIsbn(isbn)

        Assert.assertTrue(result is CommunicationResult.Success)

        val data = (result as CommunicationResult.Success).data

        Assert.assertEquals(data, GBooks(1, list))
    }

    @Test
    fun test_fetch_book_error() = runBlocking {

        val errorCode = 404
        val errorResponseBody =
            ResponseBody.create("application/json".toMediaTypeOrNull(), "Error body content")

        coEvery {
            api.getBookByIsbn("", "user-1")
        } returns Response.error(errorCode, errorResponseBody)

        val result = repository.getBookByIsbn(isbn)

        Assert.assertTrue(result is CommunicationResult.Error)

    }

    @Test
    fun test_fetch_books_exception() = runBlocking {
        coEvery { api.getBookByIsbn("","") } throws Exception()

        val result = repository.getBookByIsbn(isbn)

        Assert.assertTrue(result is CommunicationResult.Exception)
    }

    @Test
    fun test_fetch_book_communicationError() = runBlocking {
        coEvery { api.getBookByIsbn(isbn,"") } throws UnknownHostException()

        val result = repository.getBookByIsbn(isbn)

        Assert.assertTrue(result is CommunicationResult.CommunicationError)
    }

    @Test
    fun test_fetch_book_IOException() = runBlocking {
        coEvery { api.getBookByIsbn(isbn, "user-1") } throws IOException()

        val result = repository.getBookByIsbn(isbn)

        Assert.assertTrue(result is CommunicationResult.Exception)
    }

}