package cz.mendelu.pef.xmichl.bookaroo.communication.places

import cz.mendelu.pef.xmichl.bookaroo.architecture.CommunicationResult
import cz.mendelu.pef.xmichl.bookaroo.architecture.IBaseRemoteRepository
import cz.mendelu.pef.xmichl.bookaroo.model.GPlaces
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PlacesRemoteRepositoryImpl @Inject constructor(
    private val placesApi: PlacesApi,
) : IPlacesRemoteRepository, IBaseRemoteRepository {

    override suspend fun getBookStores()
            : CommunicationResult<GPlaces> {

//        val response = withContext(Dispatchers.IO) {
//            placesApi.getBookStores(
//                ""
//            )
//        }
//        return processResponse(response)

        return processResponse {
            placesApi.getBookStores(
                ""
            )
        }
    }

}
