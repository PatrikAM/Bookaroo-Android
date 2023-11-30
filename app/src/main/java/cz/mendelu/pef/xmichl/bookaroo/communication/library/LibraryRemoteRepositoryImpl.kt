package cz.mendelu.pef.xmichl.bookaroo.communication.library

import cz.mendelu.pef.xmichl.bookaroo.architecture.CommunicationResult
import cz.mendelu.pef.xmichl.bookaroo.architecture.IBaseRemoteRepository
import cz.mendelu.pef.xmichl.bookaroo.datastore.DataStoreRepositoryImpl
import cz.mendelu.pef.xmichl.bookaroo.model.Library
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LibraryRemoteRepositoryImpl @Inject constructor(
    private val librariesApi: LibrariesApi,
    private val dataStoreRepository: DataStoreRepositoryImpl
) : IBaseRemoteRepository, ILibraryRemoteRepository {

    override suspend fun fetchLibraries()
            : CommunicationResult<List<Library>> {
        val response = withContext(Dispatchers.IO) {
            librariesApi.fetchLibraries(dataStoreRepository.getUserToken()!!)
//            librariesApi.fetchLibraries("")
        }
        return processResponse(response)
    }

    override suspend fun createLibrary(library: Library)
            : CommunicationResult<Library> {
        val response = withContext(Dispatchers.IO) {
            librariesApi.createLibrary(
                library.name!!,
                dataStoreRepository.getUserToken()!!
//                ""
            )
        }
        return processResponse(response)
    }
}