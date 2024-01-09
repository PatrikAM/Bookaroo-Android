package cz.mendelu.pef.xmichl.bookaroo.datastore

interface IDataStoreRepository {
    suspend fun setLoginSuccessful()
    suspend fun getLoginSuccessful(): Boolean
    suspend fun setLogoutSuccessful()

    suspend fun setUserLogin()
    suspend fun getUserLogin(): String

    suspend fun setUserName()
    suspend fun getUserName(): String

    suspend fun setUserId()
    suspend fun getUserId(): String

    suspend fun setUserToken(token: String?)
    suspend fun getUserToken(): String?

    suspend fun deleteUserToken()
}
