package cz.mendelu.pef.xmichl.bookaroo.communication.reader

import cz.mendelu.pef.xmichl.bookaroo.architecture.CommunicationResult
import cz.mendelu.pef.xmichl.bookaroo.model.BookarooApiResponse
import cz.mendelu.pef.xmichl.bookaroo.model.Reader

interface IReaderRemoteRepository {

    suspend fun login(login: String, password: String): CommunicationResult<Reader>

    suspend fun register(login: String, password: String, name: String): CommunicationResult<Reader>

    suspend fun logout(): CommunicationResult<BookarooApiResponse>

    suspend fun closeAccount(): CommunicationResult<BookarooApiResponse>

}
