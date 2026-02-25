package com.ioffeivan.feature.sign_in.presentation

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.ioffeivan.feature.sign_in.BuildConfig

internal class GoogleSignInManager {
    suspend fun signIn(context: Context): GoogleIdTokenCredential? {
        val credentialManager = CredentialManager.create(context)

        val googleIdOption =
            GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(BuildConfig.GOOGLE_SERVER_CLIENT_ID)
                .build()

        val request =
            GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

        val result =
            try {
                credentialManager.getCredential(
                    context = context,
                    request = request,
                )
            } catch (_: GetCredentialException) {
                return null
            }

        val credential = result.credential

        return if (credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
            try {
                GoogleIdTokenCredential.createFrom(credential.data)
            } catch (_: Exception) {
                null
            }
        } else {
            null
        }
    }
}
