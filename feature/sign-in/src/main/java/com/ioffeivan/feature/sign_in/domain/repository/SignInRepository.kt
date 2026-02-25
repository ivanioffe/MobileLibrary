package com.ioffeivan.feature.sign_in.domain.repository

import com.ioffeivan.core.model.User
import com.ioffeivan.feature.sign_in.domain.model.AuthData
import kotlinx.coroutines.flow.Flow

interface SignInRepository {
    val isLoggedIn: Flow<Boolean>

    suspend fun saveAuthData(authData: AuthData)

    suspend fun saveUser(user: User)
}
