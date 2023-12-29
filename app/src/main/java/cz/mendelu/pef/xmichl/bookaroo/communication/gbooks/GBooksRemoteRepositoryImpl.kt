package cz.mendelu.pef.xmichl.bookaroo.communication.gbooks

import cz.mendelu.pef.xmichl.bookaroo.architecture.CommunicationResult
import cz.mendelu.pef.xmichl.bookaroo.architecture.IBaseRemoteRepository
import cz.mendelu.pef.xmichl.bookaroo.model.GBooks
import javax.inject.Inject

class GBooksRemoteRepositoryImpl @Inject constructor(
    private val gBooksApi: GBooksApi
) : IGBooksRemoteRepository, IBaseRemoteRepository {

    override suspend fun getBookByIsbn(isbn: String): CommunicationResult<GBooks> {
        return processResponse {
            gBooksApi.getBookByIsbn("isbn:$isbn")
        }
    }

}