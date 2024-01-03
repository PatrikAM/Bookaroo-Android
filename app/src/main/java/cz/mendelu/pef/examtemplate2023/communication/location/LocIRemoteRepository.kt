package cz.mendelu.pef.examtemplate2023.communication.location

import cz.mendelu.pef.examtemplate2023.architecture.CommunicationResult
import cz.mendelu.pef.examtemplate2023.architecture.IBaseRemoteRepository
import cz.mendelu.pef.examtemplate2023.model.IP


interface LocIRemoteRepository : IBaseRemoteRepository {

    suspend fun fetchLoc(ip: String): CommunicationResult<IP>

}