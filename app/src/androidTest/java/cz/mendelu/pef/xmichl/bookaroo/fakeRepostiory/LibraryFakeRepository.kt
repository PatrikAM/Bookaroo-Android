package cz.mendelu.pef.xmichl.bookaroo.fakeRepostiory

import cz.mendelu.pef.xmichl.bookaroo.architecture.CommunicationResult
import cz.mendelu.pef.xmichl.bookaroo.communication.library.ILibraryRemoteRepository
import cz.mendelu.pef.xmichl.bookaroo.mock.BokarooLibrariesServerMock
import cz.mendelu.pef.xmichl.bookaroo.model.Library
import javax.inject.Inject

class LibraryFakeRepository @Inject constructor(): ILibraryRemoteRepository {
    override suspend fun fetchLibraries(): CommunicationResult<List<Library>> {
        return CommunicationResult.Success(
            BokarooLibrariesServerMock.all
        )
    }

    override suspend fun createLibrary(library: String): CommunicationResult<Library> {
        TODO("Not yet implemented")
    }
}