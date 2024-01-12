package cz.mendelu.pef.xmichl.bookaroo.repository

import com.google.android.gms.maps.model.LatLng
import cz.mendelu.pef.xmichl.bookaroo.architecture.CommunicationResult
import cz.mendelu.pef.xmichl.bookaroo.communication.places.PlacesApi
import cz.mendelu.pef.xmichl.bookaroo.communication.places.PlacesRemoteRepositoryImpl
import cz.mendelu.pef.xmichl.bookaroo.model.BookShop
import cz.mendelu.pef.xmichl.bookaroo.model.GPlace
import cz.mendelu.pef.xmichl.bookaroo.model.GPlaces
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

class PlacesRemoteRepositoryImpl {
    private val api = mockk<PlacesApi>()

    private val repository = PlacesRemoteRepositoryImpl(api)

    private val place = GPlace(
        BookShop(
            place = "Luxor",
            place_id = "lx",
            formatted_address = "jnj",
            geometry = null,
            name = null,
            reference = null,
            icon = null,
            rating = null,
            opening_hours = null,
            photos = null,
            formatted_phone_number = null,
            international_phone_number = null,
            url = null
        )
    )

    @Test
    fun test_fetch_places_success() = runBlocking {
        val list = ArrayList<BookShop>()
        list.add(place.result)
        coEvery {
            api.getBookStores("", "")
        } returns Response.success(GPlaces("kwdaw", list))

        val result = repository.getBookStores(LatLng(0.0, 0.0))

        Assert.assertTrue(result is CommunicationResult.Success)

        val data = (result as CommunicationResult.Success).data

        Assert.assertEquals(data.results.size, 1)
        Assert.assertEquals(data.results[0], place.result)
    }

    @Test
    fun test_fetch_places_error() = runBlocking {

        val errorCode = 404
        val errorResponseBody =
            ResponseBody.create("application/json".toMediaTypeOrNull(), "Error body content")

        coEvery {
            api.getBookStores("", "")
        } returns Response.error(errorCode, errorResponseBody)

        val result = repository.getBookStores(LatLng(0.0, 0.0))

        Assert.assertTrue(result is CommunicationResult.Error)

    }

    @Test
    fun test_fetch_places_exception() = runBlocking {
        coEvery { api.getBookStores("", "") } throws Exception()

        val result = repository.getBookStores(LatLng(0.0, 0.0))

        Assert.assertTrue(result is CommunicationResult.Exception)
    }

    @Test
    fun test_fetch_places_communicationError() = runBlocking {
        coEvery { api.getBookStores("", "20.0-2C%10.0") } throws UnknownHostException()

        val result = repository.getBookStores(LatLng(20.0, 20.0))

        println(result)

        Assert.assertTrue(result is CommunicationResult.CommunicationError)
    }

    @Test
    fun test_fetch_books_IOException() = runBlocking {
        coEvery { api.getBookStores("", "") } throws IOException()

        val result = repository.getBookStores(LatLng(0.0, 0.0))

        Assert.assertTrue(result is CommunicationResult.Exception)
    }
}