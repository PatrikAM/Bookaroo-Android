package cz.mendelu.pef.xmichl.bookaroo.communication.places

import com.google.android.gms.maps.model.LatLng
import cz.mendelu.pef.xmichl.bookaroo.architecture.CommunicationResult
import cz.mendelu.pef.xmichl.bookaroo.model.GPlace
import cz.mendelu.pef.xmichl.bookaroo.model.GPlaces
import cz.mendelu.pef.xmichl.bookaroo.model.Location

interface IPlacesRemoteRepository {
    suspend fun getBookStores(location: LatLng): CommunicationResult<GPlaces>
    suspend fun getBookStoreDetail(placeId: String): CommunicationResult<GPlace>
}