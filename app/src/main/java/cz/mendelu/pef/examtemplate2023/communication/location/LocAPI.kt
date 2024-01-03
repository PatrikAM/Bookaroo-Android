package cz.mendelu.pef.examtemplate2023.communication.location

import cz.mendelu.pef.examtemplate2023.model.IP
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LocAPI {
    @GET("{ip}/{geo}/exam.json")
    suspend fun fetchLoc(
        @Path("ip") format: String,
        @Path("geo") geo: String = "geo"
    ): Response<IP>
}