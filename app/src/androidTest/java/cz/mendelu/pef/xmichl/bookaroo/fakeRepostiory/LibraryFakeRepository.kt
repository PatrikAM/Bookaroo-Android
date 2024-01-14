package cz.mendelu.pef.xmichl.bookaroo.fakeRepostiory

import cz.mendelu.pef.xmichl.bookaroo.architecture.CommunicationResult
import cz.mendelu.pef.xmichl.bookaroo.architecture.Error
import cz.mendelu.pef.xmichl.bookaroo.communication.library.ILibraryRemoteRepository
import cz.mendelu.pef.xmichl.bookaroo.mock.BookarooLibrariesServerMock
import cz.mendelu.pef.xmichl.bookaroo.model.Library
import javax.inject.Inject

class LibraryFakeRepository @Inject constructor(): ILibraryRemoteRepository {
    override suspend fun fetchLibraries(): CommunicationResult<List<Library>> {
        return CommunicationResult.Success(
            BookarooLibrariesServerMock.all
        )
    }

    override suspend fun createLibrary(library: String): CommunicationResult<Library> {
        return CommunicationResult.Success(
            Library("newlyCreated", name = library, ownerId = null)
        )
    }

    override suspend fun fetchLibrary(libraryId: String): CommunicationResult<Library> {
        return if (BookarooLibrariesServerMock
            .all
            .any { bookShop -> bookShop.id == libraryId }
            ) {
            return CommunicationResult.Success(BookarooLibrariesServerMock
                .all
                .first() { bookShop -> bookShop.id == libraryId })
        } else {
            return CommunicationResult.Error(error = Error(code = 404, message = null))
        }
    }
}