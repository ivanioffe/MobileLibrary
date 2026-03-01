package com.ioffeivan.core.network.utils

import android.content.Context
import com.google.android.gms.auth.api.identity.AuthorizationRequest
import com.google.android.gms.auth.api.identity.AuthorizationResult
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.common.api.Scope
import com.ioffeivan.core.network.BuildConfig
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class BooksAccessManager @Inject constructor(
    @param:ApplicationContext private val context: Context,
) {
    suspend fun requestAccessToBooks(): AuthorizationResult? {
        val requestedScopes = listOf(Scope(BuildConfig.GOOGLE_BOOKS_SCOPE))
        val authorizationRequest =
            AuthorizationRequest.builder()
                .setRequestedScopes(requestedScopes)
                .build()

        return try {
            Identity.getAuthorizationClient(context)
                .authorize(authorizationRequest)
                .await()
        } catch (_: Exception) {
            null
        }
    }
}
