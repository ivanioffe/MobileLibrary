package com.ioffeivan.core.network.utils

import com.ioffeivan.core.datastore_auth.AuthManager
import com.ioffeivan.core.datastore_auth.model.AuthDataLocal
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

internal class TokenAuthenticator @Inject constructor(
    private val authManager: AuthManager,
    private val booksAccessManager: BooksAccessManager,
) : Authenticator {
    private val mutex = Mutex()

    override fun authenticate(route: Route?, response: Response): Request? {
        val newAccessToken =
            runBlocking {
                mutex.withLock {
                    val tokenInRequest = response.request.getTokenFromAuthorizationHeader()
                    val savedToken = authManager.getAuthData().first().accessToken

                    if (savedToken != tokenInRequest && savedToken != null) {
                        return@runBlocking savedToken
                    }

                    val accessToken = booksAccessManager.requestAccessToBooks()?.accessToken
                    if (accessToken != null) {
                        authManager.saveAuthData(AuthDataLocal(accessToken))
                    } else {
                        authManager.deleteAuthData()
                    }

                    return@runBlocking accessToken
                }
            }

        return newAccessToken?.let {
            response.request.newBuilder()
                .authorizationHeader(newAccessToken)
                .build()
        }
    }
}
