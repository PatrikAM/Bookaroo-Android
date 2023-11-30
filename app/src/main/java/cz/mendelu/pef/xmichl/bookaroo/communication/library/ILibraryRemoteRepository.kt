package cz.mendelu.pef.xmichl.bookaroo.communication.library

import cz.mendelu.pef.xmichl.bookaroo.architecture.CommunicationResult
import cz.mendelu.pef.xmichl.bookaroo.model.Library

interface ILibraryRemoteRepository {

    suspend fun fetchLibraries(): CommunicationResult<List<Library>>

    suspend fun createLibrary(library: Library): CommunicationResult<Library>

}