package cz.mendelu.pef.examtemplate2023.architecture

import com.squareup.moshi.JsonDataException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

interface IBaseRemoteRepository {

    suspend fun <T : Any> processResponse(apiRequest: suspend () -> Response<T>)
            : CommunicationResult<T> {
        try {
            val response: Response<T> = withContext(Dispatchers.IO) {
                apiRequest()
            }
            if (response.isSuccessful) {
                return if (response.body() != null) {
                    CommunicationResult.Success(response.body()!!)
                } else {
                    CommunicationResult.Error(
                        Error(
                            response.code(), response.errorBody().toString()
                        )
                    )
                }
            } else {
                return CommunicationResult.Error(
                    Error(
                        response.code(), response.errorBody().toString()
                    )
                )
            }
        } catch (ex: UnknownHostException) {
            return CommunicationResult.CommunicationError()
        } catch (ex: SocketTimeoutException) {
            return CommunicationResult.CommunicationError()
        } catch (ex: ConnectException) {
            return CommunicationResult.CommunicationError()
        } catch (ex: JsonDataException) {
            return CommunicationResult.Exception(ex)
        } catch (ex: Exception) {
            return CommunicationResult.Exception(ex)
        }
    }

//
//    fun <T : Any> processResponse(call: Response<T>): CommunicationResult<T> {
//        try {
//            if (call.isSuccessful) {
//                call.body()?.let {
//                    return CommunicationResult.Success(it)
//                } ?: kotlin.run {
//                    return CommunicationResult.Error(
//                        CommunicationError(
//                            call.code(),
//                            call.errorBody().toString()
//                        )
//                    )
//                }
//            } else {
//                return CommunicationResult.Error(
//                    CommunicationError(
//                        call.code(),
//                        call.errorBody().toString()
//                    )
//                )
//            }
//
//        } catch (ex: UnknownHostException) {
//            return CommunicationResult.Exception(ex)
//        } catch (socketException: SocketTimeoutException) {
//            return CommunicationResult.ConnectionError()
//        } catch (ex: Exception) {
//            return CommunicationResult.Exception(ex)
//        }
//    }
}