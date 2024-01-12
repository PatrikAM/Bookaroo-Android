package cz.mendelu.pef.xmichl.bookaroo.repository

import cz.mendelu.pef.xmichl.bookaroo.architecture.CommunicationResult
import cz.mendelu.pef.xmichl.bookaroo.communication.reader.ReaderApi
import cz.mendelu.pef.xmichl.bookaroo.communication.reader.ReaderRemoteRepositoryImpl
import cz.mendelu.pef.xmichl.bookaroo.datastore.DataStoreRepositoryImpl
import cz.mendelu.pef.xmichl.bookaroo.model.Reader
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

class ReadersRemoteRepositoryImplUnitTests {
    private val api = mockk<ReaderApi>()
    private val dataStore = mockk<DataStoreRepositoryImpl>()

    private val repository = ReaderRemoteRepositoryImpl(api)

    val email = "exam@es.dom"
    val password = "password.23"
    val name = "john"

    val reader = Reader(
        name = "john",
        login = email,
        token = "token",
        id = "id"
    )

    @Test
    fun test_register_success() = runBlocking {
        coEvery {
            api.register(email, password, name)
        } returns Response.success(reader)

        val result = repository.register(email, password, name)

        Assert.assertTrue(result is CommunicationResult.Success)

        val data = (result as CommunicationResult.Success).data

        Assert.assertEquals(data, reader)
    }

    @Test
    fun test_register_error() = runBlocking {

        val errorCode = 404
        val errorResponseBody =
            ResponseBody.create("application/json".toMediaTypeOrNull(), "Error body content")

        coEvery {
            api.register(email, password, name)
        } returns Response.error(errorCode, errorResponseBody)

        val result = repository.register(email, password, name)

        Assert.assertTrue(result is CommunicationResult.Error)

    }

    @Test
    fun test_register_exception() = runBlocking {
        coEvery { api.register(email, password, name) } throws Exception()

        val result = repository.register(email, password, name)

        Assert.assertTrue(result is CommunicationResult.Exception)
    }

    @Test
    fun test_register_communicationError() = runBlocking {
        coEvery { api.register(email, password, name) } throws UnknownHostException()

        val result = repository.register(email, password, name)

        Assert.assertTrue(result is CommunicationResult.CommunicationError)
    }

    @Test
    fun test_register_IOException() = runBlocking {
        coEvery { api.register(email, password, name) } throws IOException()

        val result = repository.register(email, password, name)

        Assert.assertTrue(result is CommunicationResult.Exception)
    }

    @Test
    fun test_login_success() = runBlocking {
        coEvery {
            api.login(email, password)
        } returns Response.success(reader)

        val result = repository.login(email, password)

        Assert.assertTrue(result is CommunicationResult.Success)

        val data = (result as CommunicationResult.Success).data

        Assert.assertEquals(data, reader)
    }

    @Test
    fun test_login_error() = runBlocking {

        val errorCode = 404
        val errorResponseBody =
            ResponseBody.create("application/json".toMediaTypeOrNull(), "Error body content")

        coEvery {
            api.login(email, password)
        } returns Response.error(errorCode, errorResponseBody)

        val result = repository.login(email, password)

        Assert.assertTrue(result is CommunicationResult.Error)

    }

    @Test
    fun test_login_exception() = runBlocking {
        coEvery { api.login(email, password) } throws Exception()

        val result = repository.login(email, password)

        Assert.assertTrue(result is CommunicationResult.Exception)
    }

    @Test
    fun test_login_communicationError() = runBlocking {
        coEvery { api.login(email, password) } throws UnknownHostException()

        val result = repository.login(email, password)

        Assert.assertTrue(result is CommunicationResult.CommunicationError)
    }

    @Test
    fun test_login_IOException() = runBlocking {
        coEvery { api.login(email, password) } throws IOException()

        val result = repository.login(email, password)

        Assert.assertTrue(result is CommunicationResult.Exception)
    }
}