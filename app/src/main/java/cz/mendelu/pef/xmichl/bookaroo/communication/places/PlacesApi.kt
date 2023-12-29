package cz.mendelu.pef.xmichl.bookaroo.communication.places

import cz.mendelu.pef.xmichl.bookaroo.model.BookShop
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PlacesApi {

    @GET("textsearch")
    suspend fun getBookStores(
        @Query("name") name: String,
        @Query("type") type: String = "book_store",
        @Query("key") key: String
    ) : Response<List<BookShop>>

}