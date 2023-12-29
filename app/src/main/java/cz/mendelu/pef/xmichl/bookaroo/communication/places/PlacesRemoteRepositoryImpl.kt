package cz.mendelu.pef.xmichl.bookaroo.communication.places

import javax.inject.Inject

class PlacesRemoteRepositoryImpl @Inject constructor(
    private val placesApi: PlacesApi,
): IPlacesRemoteRepository {

    override fun getBookStores() {
        TODO("Not yet implemented")
    }

}