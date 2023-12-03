package cz.mendelu.pef.xmichl.bookaroo.architecture

import com.squareup.moshi.JsonDataException
import cz.mendelu.pef.xmichl.bookaroo.model.Library
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException

interface IBaseRemoteRepository {

    fun <T : Any> processResponse(response: Response<T>): CommunicationResult<T> {
        try {

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
        } catch (ex: JsonDataException) {
            return CommunicationResult.Exception(ex)
        } catch (ex: Exception) {
            return CommunicationResult.Exception(ex)
        }
    }

    suspend fun <T : Any> processResponse(apiCall: suspend () -> Response<T>): CommunicationResult<T> {
        try {
            val response: Response<T> = withContext(Dispatchers.IO) {
                apiCall()
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
        } catch (ex: JsonDataException) {
            return CommunicationResult.Exception(ex)
        } catch (ex: Exception) {
            return CommunicationResult.Exception(ex)
        }
    }

}
