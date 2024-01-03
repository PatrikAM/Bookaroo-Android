package cz.mendelu.pef.xmichl.bookaroo.communication.places

import cz.mendelu.pef.xmichl.bookaroo.architecture.CommunicationResult
import cz.mendelu.pef.xmichl.bookaroo.model.GPlaces

interface IPlacesRemoteRepository {
    suspend fun getBookStores(): CommunicationResult<GPlaces>
}