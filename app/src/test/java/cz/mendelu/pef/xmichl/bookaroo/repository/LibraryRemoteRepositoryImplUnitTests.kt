package cz.mendelu.pef.xmichl.bookaroo.repository

import cz.mendelu.pef.xmichl.bookaroo.architecture.CommunicationResult
import cz.mendelu.pef.xmichl.bookaroo.communication.library.LibrariesApi
import cz.mendelu.pef.xmichl.bookaroo.communication.library.LibraryRemoteRepositoryImpl
import cz.mendelu.pef.xmichl.bookaroo.datastore.DataStoreRepositoryImpl
import cz.mendelu.pef.xmichl.bookaroo.mock.BookarooLibrariesServerMock
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

class LibraryRemoteRepositoryImplUnitTests {

    private val api = mockk<LibrariesApi>()
    private val dataStore = mockk<DataStoreRepositoryImpl>()

    private val repository = LibraryRemoteRepositoryImpl(api, dataStore)

    private val libs = BookarooLibrariesServerMock.all
    private val lib = BookarooLibrariesServerMock.lib1

    @Test
    fun test_fetch_libraries_success() = runBlocking {
        coEvery {
            api.fetchLibraries("user-1")
        } returns Response.success(libs)

        coEvery {
            dataStore.getUserToken()
        } returns "user-1"

        val result = repository.fetchLibraries()

        Assert.assertTrue(result is CommunicationResult.Success)

        val data = (result as CommunicationResult.Success).data

        Assert.assertEquals(data.size, libs.size)
        libs.forEach {
            Assert.assertTrue(data.contains(it))
        }
    }

    @Test
    fun test_fetch_libraries_error() = runBlocking {

        val errorCode = 404
        val errorResponseBody =
            ResponseBody.create("application/json".toMediaTypeOrNull(), "Error body content")

        coEvery {
            api.fetchLibraries("user-1")
        } returns Response.error(errorCode, errorResponseBody)

        coEvery {
            dataStore.getUserToken()
        } returns "user-1"

        val result = repository.fetchLibraries()

        Assert.assertTrue(result is CommunicationResult.Error)

    }

    @Test
    fun test_fetch_libraries_exception() = runBlocking {
        coEvery { api.fetchLibraries("user-1") } throws Exception()
        coEvery {
            dataStore.getUserToken()
        } returns "user-1"

        val result = repository.fetchLibraries()

        Assert.assertTrue(result is CommunicationResult.Exception)
    }

    @Test
    fun test_fetch_libraries_communicationError() = runBlocking {
        coEvery { api.fetchLibraries("user-1") } throws UnknownHostException()
        coEvery {
            dataStore.getUserToken()
        } returns "user-1"

        val result = repository.fetchLibraries()

        Assert.assertTrue(result is CommunicationResult.CommunicationError)
    }

    @Test
    fun test_fetch_libraries_IOException() = runBlocking {
        coEvery { api.fetchLibraries("user-1") } throws IOException()
        coEvery {
            dataStore.getUserToken()
        } returns "user-1"

        val result = repository.fetchLibraries()

        Assert.assertTrue(result is CommunicationResult.Exception)
    }

    @Test
    fun test_create_library_success() = runBlocking {

        coEvery {
            api.createLibrary(lib.name!!, "user-1")
        } returns Response.success(lib)

        coEvery {
            dataStore.getUserToken()
        } returns "user-1"

        val result = repository.createLibrary(lib.name!!)

        Assert.assertTrue(result is CommunicationResult.Success)

        val data = (result as CommunicationResult.Success).data

        Assert.assertEquals(data, lib)
    }

    @Test
    fun test_create_library_error() = runBlocking {
        val errorCode = 404
        val errorResponseBody =
            ResponseBody.create("application/json".toMediaTypeOrNull(), "Error body content")

        coEvery {
            api.createLibrary(lib.name!!, "user-1")
        }  returns Response.error(errorCode, errorResponseBody)

        coEvery {
            dataStore.getUserToken()
        } returns "user-1"

        val result = repository.createLibrary(lib.name!!)

        Assert.assertTrue(result is CommunicationResult.Error)

    }

    @Test
    fun test_create_library_exception() = runBlocking {
        coEvery {
            api.createLibrary(lib.name!!, "user-1")
        } throws Exception()
        coEvery {
            dataStore.getUserToken()
        } returns "user-1"

        val result = repository.createLibrary(lib.name!!)

        Assert.assertTrue(result is CommunicationResult.Exception)
    }

    @Test
    fun test_create_library_communicationError() = runBlocking {
        coEvery {
            api.createLibrary(lib.name!!, "user-1")
        } throws UnknownHostException()
        coEvery {
            dataStore.getUserToken()
        } returns "user-1"

        val result = repository.createLibrary(lib.name!!)

        Assert.assertTrue(result is CommunicationResult.CommunicationError)
    }

    @Test
    fun test_create_library_IOException() = runBlocking {
        coEvery {
            api.createLibrary(lib.name!!, "user-1")
        } throws IOException()
        coEvery {
            dataStore.getUserToken()
        } returns "user-1"

        val result = repository.createLibrary(lib.name!!)

        Assert.assertTrue(result is CommunicationResult.Exception)
    }

}