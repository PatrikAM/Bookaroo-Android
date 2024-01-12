package cz.mendelu.pef.xmichl.bookaroo.fakeRepostiory

import cz.mendelu.pef.xmichl.bookaroo.architecture.CommunicationResult
import cz.mendelu.pef.xmichl.bookaroo.communication.gbooks.IGBooksRemoteRepository
import cz.mendelu.pef.xmichl.bookaroo.model.GBooks
import javax.inject.Inject


// Untestable usecase - it is get from camera
class GBooksFakeRepository @Inject constructor(): IGBooksRemoteRepository {
    override suspend fun getBookByIsbn(isbn: String): CommunicationResult<GBooks> {
        TODO("Not yet implemented")
    }
}