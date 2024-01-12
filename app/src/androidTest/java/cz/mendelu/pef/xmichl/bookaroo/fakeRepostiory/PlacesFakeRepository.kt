package cz.mendelu.pef.xmichl.bookaroo.fakeRepostiory

import com.google.android.gms.maps.model.LatLng
import cz.mendelu.pef.xmichl.bookaroo.architecture.CommunicationResult
import cz.mendelu.pef.xmichl.bookaroo.architecture.Error
import cz.mendelu.pef.xmichl.bookaroo.communication.places.IPlacesRemoteRepository
import cz.mendelu.pef.xmichl.bookaroo.mock.GoogleApiMock
import cz.mendelu.pef.xmichl.bookaroo.model.GPlace
import cz.mendelu.pef.xmichl.bookaroo.model.GPlaces
import javax.inject.Inject

class PlacesFakeRepository @Inject constructor() : IPlacesRemoteRepository {
    override suspend fun getBookStores(location: LatLng): CommunicationResult<GPlaces> {
        return CommunicationResult.Success(GoogleApiMock.gPlaces)
    }

    override suspend fun getBookStoreDetail(placeId: String): CommunicationResult<GPlace> {
        return if (
            GoogleApiMock
                .gPlaces
                .results.any { bookShop -> bookShop.place_id == placeId }
        ) {
            CommunicationResult.Success(
                GPlace(
                    GoogleApiMock
                        .gPlaces
                        .results.first { bookShop -> bookShop.place_id == placeId }
                )
            )
        } else {
            CommunicationResult
                .Error(error = Error(code = 404, message = null))
        }
    }
}