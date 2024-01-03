package cz.mendelu.pef.examtemplate2023.communication.ip

import cz.mendelu.pef.examtemplate2023.model.IP
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface API {
    @GET("/")
    suspend fun fetchIp(@Query("format") format: String = "json") : Response<IP>
}