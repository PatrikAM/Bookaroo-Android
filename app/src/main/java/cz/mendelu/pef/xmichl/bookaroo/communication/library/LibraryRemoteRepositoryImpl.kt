package cz.mendelu.pef.xmichl.bookaroo.communication.library

import cz.mendelu.pef.xmichl.bookaroo.architecture.CommunicationResult
import cz.mendelu.pef.xmichl.bookaroo.architecture.IBaseRemoteRepository
import cz.mendelu.pef.xmichl.bookaroo.datastore.DataStoreRepositoryImpl
import cz.mendelu.pef.xmichl.bookaroo.model.Library
import javax.inject.Inject

class LibraryRemoteRepositoryImpl @Inject constructor(
    private val librariesApi: LibrariesApi,
    private val dataStoreRepository: DataStoreRepositoryImpl
) : IBaseRemoteRepository, ILibraryRemoteRepository {

    override suspend fun fetchLibraries()
            : CommunicationResult<List<Library>> {
//        val response = withContext(Dispatchers.IO) {
//            librariesApi.fetchLibraries(dataStoreRepository.getUserToken()!!)
//        }
//        return processResponse(response)
        return processResponse {
            librariesApi.fetchLibraries(
                dataStoreRepository.getUserToken()!!
            )
        }
    }

    override suspend fun createLibrary(library: String)
            : CommunicationResult<Library> {

        return processResponse {
            librariesApi.createLibrary(
                library,
                dataStoreRepository.getUserToken()!!
            )
        }
    }
}