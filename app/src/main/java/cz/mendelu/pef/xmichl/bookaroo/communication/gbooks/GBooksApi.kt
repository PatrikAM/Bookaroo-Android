package cz.mendelu.pef.xmichl.bookaroo.communication.gbooks

import cz.mendelu.pef.xmichl.bookaroo.model.GBooks
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GBooksApi {

    @GET("volumes")
    suspend fun getBookByIsbn(
        @Query("q") q: String,
        @Query("token") token: String = "AIzaSyCNy-ufOm_1_eiAOMIhFrMGBqMIoSAl7ho"
    ) : Response<GBooks>

}