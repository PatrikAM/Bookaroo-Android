package cz.mendelu.pef.xmichl.bookaroo.communication.reader

import cz.mendelu.pef.xmichl.bookaroo.model.BookarooApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ReaderApi {

    @GET("user/login")
    suspend fun login(
        @Query("login") login: String,
        @Query("password") password: String
    ) : Response<BookarooApiResponse>

    @GET("user/logout")
    suspend fun logout(
    ) : Response<BookarooApiResponse>

    @GET("user/close_account")
    suspend fun closeAccount(
    ) : Response<BookarooApiResponse>

    @POST("user/register")
    suspend fun register(
        @Query("login") login: String,
        @Query("password") password: String,
        @Query("name") name: String
    ) : Response<BookarooApiResponse>

}
