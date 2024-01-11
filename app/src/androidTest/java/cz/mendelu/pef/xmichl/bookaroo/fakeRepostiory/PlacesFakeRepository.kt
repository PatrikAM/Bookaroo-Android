package cz.mendelu.pef.xmichl.bookaroo.fakeRepostiory

import com.google.android.gms.maps.model.LatLng
import cz.mendelu.pef.xmichl.bookaroo.architecture.CommunicationResult
import cz.mendelu.pef.xmichl.bookaroo.communication.places.IPlacesRemoteRepository
import cz.mendelu.pef.xmichl.bookaroo.model.GPlace
import cz.mendelu.pef.xmichl.bookaroo.model.GPlaces
import javax.inject.Inject

class PlacesFakeRepository @Inject constructor(): IPlacesRemoteRepository {
    override suspend fun getBookStores(location: LatLng): CommunicationResult<GPlaces> {
        TODO("Not yet implemented")
    }

    override suspend fun getBookStoreDetail(placeId: String): CommunicationResult<GPlace> {
        TODO("Not yet implemented")
    }
}