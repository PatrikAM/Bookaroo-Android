package cz.mendelu.pef.examtemplate2023.communication.ip

import cz.mendelu.pef.examtemplate2023.architecture.CommunicationResult
import cz.mendelu.pef.examtemplate2023.model.IP
import javax.inject.Inject

class RemoteRepositoryImpl
    @Inject constructor(private val api: API) : IRemoteRepository {
    override suspend fun fetchIp(): CommunicationResult<IP> {
        return processResponse {
            api.fetchIp()
        }
    }

}