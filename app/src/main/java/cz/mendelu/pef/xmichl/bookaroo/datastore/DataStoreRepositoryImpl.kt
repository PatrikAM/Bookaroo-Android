package cz.mendelu.pef.xmichl.bookaroo.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import dagger.multibindings.StringKey
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(private val context: Context) :
    IDataStoreRepository {

    override suspend fun setLoginSuccessful() {
        setLoginSuccessfulPrefKey(true)
    }

    private suspend fun setLoginSuccessfulPrefKey(value: Boolean) {
        val preferencesKey = booleanPreferencesKey(DataStoreConstants.LOGIN_SUCCESSFUL)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    private suspend fun setStringPrefKey(value: String?, key: String) {
        val preferencesKey = stringPreferencesKey(key)
        context.dataStore.edit { preferences ->
            if (value != null) {
                preferences[preferencesKey] = value
            } else {
                preferences.remove(preferencesKey)
            }
        }
    }

    private suspend fun getStringPrefKey(key: String): String? {
        return try {
            val preferencesKey = stringPreferencesKey(key)
            val preferences = context.dataStore.data.first()
            if (!preferences.contains(preferencesKey))
                null
            else
                preferences[preferencesKey]
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun setLogoutSuccessful() {
        setLoginSuccessfulPrefKey(false)
    }

    override suspend fun setUserLogin() {
        TODO("Not yet implemented")
    }

    override suspend fun getUserLogin(): String {
        TODO("Not yet implemented")
    }

    override suspend fun setUserName() {
        TODO("Not yet implemented")
    }

    override suspend fun getUserName(): String {
        TODO("Not yet implemented")
    }

    override suspend fun setUserId() {
        TODO("Not yet implemented")
    }

    override suspend fun getUserId(): String {
        TODO("Not yet implemented")
    }

    override suspend fun setUserToken(token: String?) {
        setStringPrefKey(token, DataStoreConstants.USER_TOKEN)
    }

    override suspend fun getUserToken(): String? {
        return getStringPrefKey(DataStoreConstants.USER_TOKEN)
    }

    override suspend fun getLoginSuccessful(): Boolean {
        return try {
            val preferencesKey = booleanPreferencesKey(DataStoreConstants.LOGIN_SUCCESSFUL)
            val preferences = context.dataStore.data.first()
            if (!preferences.contains(preferencesKey))
                false
            else
                preferences[preferencesKey]!!
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}