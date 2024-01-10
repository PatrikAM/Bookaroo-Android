package cz.mendelu.pef.xmichl.bookaroo.fakeRepostiory

import cz.mendelu.pef.xmichl.bookaroo.architecture.CommunicationResult
import cz.mendelu.pef.xmichl.bookaroo.communication.library.ILibraryRemoteRepository
import cz.mendelu.pef.xmichl.bookaroo.model.Library

class LibraryFakeRepository: ILibraryRemoteRepository {
    override suspend fun fetchLibraries(): CommunicationResult<List<Library>> {
        TODO("Not yet implemented")
    }

    override suspend fun createLibrary(library: String): CommunicationResult<Library> {
        TODO("Not yet implemented")
    }
}