package cz.mendelu.pef.xmichl.bookaroo.fakeRepostiory

import cz.mendelu.pef.xmichl.bookaroo.architecture.CommunicationResult
import cz.mendelu.pef.xmichl.bookaroo.communication.gbooks.IGBooksRemoteRepository
import cz.mendelu.pef.xmichl.bookaroo.model.GBooks

class GBooksFakeRepository: IGBooksRemoteRepository {
    override suspend fun getBookByIsbn(isbn: String): CommunicationResult<GBooks> {
        TODO("Not yet implemented")
    }
}