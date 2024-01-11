package cz.mendelu.pef.xmichl.bookaroo.communication.reader

import cz.mendelu.pef.xmichl.bookaroo.architecture.CommunicationResult
import cz.mendelu.pef.xmichl.bookaroo.architecture.IBaseRemoteRepository
import cz.mendelu.pef.xmichl.bookaroo.model.BookarooApiResponse
import cz.mendelu.pef.xmichl.bookaroo.model.Reader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ReaderRemoteRepositoryImpl @Inject constructor(private val readerApi: ReaderApi)
    : IBaseRemoteRepository, IReaderRemoteRepository {

    override suspend fun login(login: String, password: String): CommunicationResult<Reader> {

        return processResponse {
            readerApi.login(
                login = login,
                password = password
            )
        }

//        val response = withContext(Dispatchers.IO) {
//            readerApi.login(login = login, password = password)
//        }
//        return processResponse(response)
    }

    override suspend fun register(login: String, password: String, name: String): CommunicationResult<Reader> {

        return processResponse {
            readerApi.register(
                login = login,
                password = password,
                name = name
            )
        }

//        val response = withContext(Dispatchers.IO) {
//            readerApi.register(login = login, password = password, name = name)
//        }
//        return processResponse(response)
    }

    override suspend fun logout(): CommunicationResult<BookarooApiResponse> {

        return processResponse {
            readerApi.logout()
        }

//        val response = withContext(Dispatchers.IO) {
//            readerApi.logout()
//        }
//        return processResponse(response)
    }

    override suspend fun closeAccount(): CommunicationResult<BookarooApiResponse> {
        return processResponse {
            readerApi.closeAccount()
        }
        
//        val response = withContext(Dispatchers.IO) {
//            readerApi.closeAccount()
//        }
//        return processResponse(response)
    }

}
