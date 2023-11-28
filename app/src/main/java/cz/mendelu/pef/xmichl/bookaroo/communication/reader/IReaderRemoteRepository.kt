package cz.mendelu.pef.xmichl.bookaroo.communication.reader

import cz.mendelu.pef.xmichl.bookaroo.architecture.CommunicationResult
import cz.mendelu.pef.xmichl.bookaroo.model.BookarooApiResponse

interface IReaderRemoteRepository {

    suspend fun login(login: String, password: String): CommunicationResult<BookarooApiResponse>

    suspend fun register(login: String, password: String, name: String): CommunicationResult<BookarooApiResponse>

    suspend fun logout(): CommunicationResult<BookarooApiResponse>

    suspend fun closeAccount(): CommunicationResult<BookarooApiResponse>

}
