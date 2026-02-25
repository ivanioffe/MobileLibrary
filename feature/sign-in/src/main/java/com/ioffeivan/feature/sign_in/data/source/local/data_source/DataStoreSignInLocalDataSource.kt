package com.ioffeivan.feature.sign_in.data.source.local.data_source

import com.ioffeivan.core.datastore_auth.AuthManager
import com.ioffeivan.core.datastore_auth.model.AuthDataLocal
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataStoreSignInLocalDataSource @Inject constructor(
    private val authManager: AuthManager,
) : SignInLocalDataSource {
    override val isLoggedIn: Flow<Boolean> = authManager.isLoggedIn

    override suspend fun saveAuthData(authData: AuthDataLocal) {
        authManager.saveAuthData(authData)
    }
}
