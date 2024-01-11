package cz.mendelu.pef.xmichl.bookaroo.communication.places

import android.util.Log
import com.google.android.gms.maps.model.LatLng
import cz.mendelu.pef.xmichl.bookaroo.architecture.CommunicationResult
import cz.mendelu.pef.xmichl.bookaroo.architecture.IBaseRemoteRepository
import cz.mendelu.pef.xmichl.bookaroo.model.GPlace
import cz.mendelu.pef.xmichl.bookaroo.model.GPlaces
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PlacesRemoteRepositoryImpl @Inject constructor(
    private val placesApi: PlacesApi,
) : IPlacesRemoteRepository, IBaseRemoteRepository {

    override suspend fun getBookStores(location: LatLng)
            : CommunicationResult<GPlaces> {

//        val response = withContext(Dispatchers.IO) {
//            placesApi.getBookStores(
//                ""
//            )
//        }
//        return processResponse(response)

        return processResponse {
//            Log.d("location", "${location.latitude}%2C${location.longitude}")
            placesApi.getBookStores(
                "",
                location = "${location.latitude}%2C${location.longitude}"
            )
        }
    }

    override suspend fun getBookStoreDetail(placeId: String): CommunicationResult<GPlace> {
//                val response = withContext(Dispatchers.IO) {
//            placesApi.getBookStoreDetail(
//                placeId
//            )
//        }
//        return processResponse(response)
        return processResponse {
            placesApi.getBookStoreDetail(
                placeId = placeId
            )
        }
    }

}
