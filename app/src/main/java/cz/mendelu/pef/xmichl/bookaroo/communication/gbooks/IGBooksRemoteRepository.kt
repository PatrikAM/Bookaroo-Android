package cz.mendelu.pef.xmichl.bookaroo.communication.gbooks

import cz.mendelu.pef.xmichl.bookaroo.architecture.CommunicationResult
import cz.mendelu.pef.xmichl.bookaroo.model.GBooks

interface IGBooksRemoteRepository {

    suspend fun getBookByIsbn(isbn: String): CommunicationResult<GBooks>

}