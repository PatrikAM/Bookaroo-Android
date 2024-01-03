package cz.mendelu.pef.examtemplate2023.communication.ip

import cz.mendelu.pef.examtemplate2023.architecture.CommunicationResult
import cz.mendelu.pef.examtemplate2023.architecture.IBaseRemoteRepository
import cz.mendelu.pef.examtemplate2023.model.IP


interface IRemoteRepository : IBaseRemoteRepository {

    suspend fun fetchIp(): CommunicationResult<IP>

}