package com.ioffeivan.feature.sign_in.data.repository

import com.ioffeivan.core.data.mapper.toUserLocal
import com.ioffeivan.core.model.User
import com.ioffeivan.feature.sign_in.data.mapper.toAuthDataLocal
import com.ioffeivan.feature.sign_in.data.source.local.data_source.SignInLocalDataSource
import com.ioffeivan.feature.sign_in.data.source.local.data_source.UserLocalDataSource
import com.ioffeivan.feature.sign_in.domain.model.AuthData
import com.ioffeivan.feature.sign_in.domain.repository.SignInRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignInRepositoryImpl @Inject constructor(
    private val signInLocalDataSource: SignInLocalDataSource,
    private val userLocalDataSource: UserLocalDataSource,
) : SignInRepository {
    override val isLoggedIn: Flow<Boolean> = signInLocalDataSource.isLoggedIn

    override suspend fun saveAuthData(authData: AuthData) {
        signInLocalDataSource.saveAuthData(authData.toAuthDataLocal())
    }

    override suspend fun saveUser(user: User) {
        userLocalDataSource.saveUser(user.toUserLocal())
    }
}
