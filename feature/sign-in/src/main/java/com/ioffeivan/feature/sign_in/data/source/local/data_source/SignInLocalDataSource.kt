package com.ioffeivan.feature.sign_in.data.source.local.data_source

import com.ioffeivan.core.datastore_auth.model.AuthDataLocal
import kotlinx.coroutines.flow.Flow

interface SignInLocalDataSource {
    val isLoggedIn: Flow<Boolean>

    suspend fun saveAuthData(authData: AuthDataLocal)
}
