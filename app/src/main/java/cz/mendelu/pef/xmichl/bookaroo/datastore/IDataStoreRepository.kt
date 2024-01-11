package cz.mendelu.pef.xmichl.bookaroo.datastore

interface IDataStoreRepository {
//    suspend fun setLoginSuccessful()
//    suspend fun getLoginSuccessful(): Boolean
//    suspend fun setLogoutSuccessful()

    suspend fun setUserToken(token: String?)
    suspend fun getUserToken(): String?

    suspend fun deleteUserToken()
}
