package cz.mendelu.pef.xmichl.bookaroo.communication.places

import cz.mendelu.pef.xmichl.bookaroo.model.GPlaces
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PlacesApi {

    @GET("textsearch/json")
    suspend fun getBookStores(
        @Query("name") name: String,
        @Query("location") location: String = "50.2092992056083%2C-15.83082603823522",
        @Query("radius") radius: Double = 1000.0,
        @Query("type") type: String = "book_store",
        @Query("key") key: String = "AIzaSyCNy-ufOm_1_eiAOMIhFrMGBqMIoSAl7ho"
    ) : Response<GPlaces>

}