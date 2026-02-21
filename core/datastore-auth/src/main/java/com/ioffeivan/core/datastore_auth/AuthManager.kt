package com.ioffeivan.core.datastore_auth

import androidx.datastore.core.DataStore
import com.ioffeivan.core.datastore_auth.model.AuthDataLocal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthManager @Inject constructor(
    private val dataStore: DataStore<AuthDataLocal>,
) {
    val isLoggedIn = dataStore.data.map { it.accessToken != null }

    fun getAuthData(): Flow<AuthDataLocal> = dataStore.data

    suspend fun saveAuthData(authDataLocal: AuthDataLocal) {
        dataStore.updateData { authDataLocal }
    }

    suspend fun deleteAuthData() {
        dataStore.updateData { AuthDataLocal() }
    }
}
