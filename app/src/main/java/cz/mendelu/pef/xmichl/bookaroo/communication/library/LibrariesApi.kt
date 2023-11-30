package cz.mendelu.pef.xmichl.bookaroo.communication.library

import cz.mendelu.pef.xmichl.bookaroo.model.Library
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface LibrariesApi {
    @POST("library/all_libraries")
    suspend fun createLibrary(
        @Query("name") name: String,
        @Query("token") token: String
    ) : Response<Library>

    @GET("library/all_libraries")
    suspend fun fetchLibraries(
        @Query("token") token: String
    ) : Response<List<Library>>
}