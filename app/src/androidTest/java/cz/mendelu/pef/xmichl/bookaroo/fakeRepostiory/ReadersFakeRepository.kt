package cz.mendelu.pef.xmichl.bookaroo.fakeRepostiory

import cz.mendelu.pef.xmichl.bookaroo.architecture.CommunicationResult
import cz.mendelu.pef.xmichl.bookaroo.communication.reader.IReaderRemoteRepository
import cz.mendelu.pef.xmichl.bookaroo.model.BookarooApiResponse
import cz.mendelu.pef.xmichl.bookaroo.model.Reader

class ReadersFakeRepository: IReaderRemoteRepository {
    override suspend fun login(login: String, password: String): CommunicationResult<Reader> {
        TODO("Not yet implemented")
    }

    override suspend fun register(
        login: String,
        password: String,
        name: String
    ): CommunicationResult<Reader> {
        TODO("Not yet implemented")
    }

    override suspend fun logout(): CommunicationResult<BookarooApiResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun closeAccount(): CommunicationResult<BookarooApiResponse> {
        TODO("Not yet implemented")
    }

}