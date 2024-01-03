package cz.mendelu.pef.examtemplate2023.communication.location

import cz.mendelu.pef.examtemplate2023.architecture.CommunicationResult
import cz.mendelu.pef.examtemplate2023.model.IP
import javax.inject.Inject

class LocRemoteRepositoryImpl
    @Inject constructor(private val api: LocAPI) : LocIRemoteRepository {
    override suspend fun fetchLoc(ip: String): CommunicationResult<IP> {
        return processResponse {
            api.fetchLoc(ip)
        }
    }

}